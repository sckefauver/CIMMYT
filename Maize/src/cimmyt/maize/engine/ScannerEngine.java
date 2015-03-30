package cimmyt.maize.engine;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.BackgroundSubtracter;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.filter.RankFilters;
import ij.plugin.frame.RoiManager;
import ij.process.ImageConverter;
import ij.text.TextPanel;
import ij.text.TextWindow;
import java.awt.Color;
import java.awt.Window;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cimmyt.maize.options.AnalysisOption;
import cimmyt.maize.options.AnalysisOptions;
import cimmyt.maize.options.ClaheOptions;
import cimmyt.maize.options.ParticleAnalysisDefaultOptions;
import cimmyt.maize.options.ParticleAnalysisOptions;
import cimmyt.maize.options.ProcessOption;
import cimmyt.maize.options.ProcessOptions;
import cimmyt.maize.options.RemoveOutlierOptions;
import cimmyt.maize.options.SubtractBackgroundOptions;
import cimmyt.maize.options.ThresholdOptions;
import cimmyt.maize.plugins.RGB_Measure_Plus;
import cimmyt.maize.plugins.RgbMeasure;
import cimmyt.maize.ui.analysis.SummaryResults;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 20, 2015
 *
 */
public class ScannerEngine {

        private List<ProcessOptions> processOptionsList = new ArrayList<ProcessOptions>();
        private List<AnalysisOptions> analysisOptionsList = new ArrayList<AnalysisOptions>();
        private ParticleAnalysisDefaultOptions defaultAnalysisOptions = null;
        private File[] selectedFiles = null;
        private RoiManager roiManager = null;
        
        private static final int CLEAR_SUMMARY_MAX = 800;
        private BufferedWriter summaryWriter = null;
        private int summaryLineCounter = 0;
        private boolean printSummaryHeadings = true;
        
        private boolean isSaveProcessedImages = false;
        private String saveProcessedImagesDir = null;
        
        public ScannerEngine() {
                
        }
        
        private void createRoiManager() {
                roiManager = new RoiManager(true);
        }
        
        private void destroyRoiManager() {
                roiManager.setVisible(false);
                roiManager.close();
                roiManager.dispose();
        }
        
        public final void addProcessOption(ProcessOptions processOptions) throws NullPointerException {
                if(processOptions == null) {
                        throw new NullPointerException("processOptions cannot be null");
                }
                
                processOptionsList.add(processOptions);
        }
        
        public final void addAnalysisOption(AnalysisOptions options) throws NullPointerException {
                if(options == null) {
                        throw new NullPointerException("options cannot be null");
                }
                
                analysisOptionsList.add(options);
        }
        
        public final void setSelectedFiles(File[] selectedFiles) throws NullPointerException {
                if(selectedFiles == null) {
                        throw new NullPointerException("selectedFiles cannot be null");
                }
                
                this.selectedFiles = selectedFiles;
        }
        
        public final void setDefaultAnalysisOptions(ParticleAnalysisDefaultOptions defaultAnalysisOptions) {
                this.defaultAnalysisOptions = defaultAnalysisOptions; 
        }
        
        public final boolean hasDefaultAnalysisOptions() {
                return defaultAnalysisOptions != null;
        }
        
        public final void setSaveProcessedImages(boolean isSaveProcessedImages) {
                this.isSaveProcessedImages = isSaveProcessedImages;
        }

        public final String getSaveProcessedImagesDir() {
                return saveProcessedImagesDir;
        }

        public final void setSaveProcessedImagesDir(String saveProcessedImagesDir) throws NullPointerException {
                if(saveProcessedImagesDir == null) {
                        throw new NullPointerException("saveProcessedImagesDir cannot be null");
                }
                
                this.saveProcessedImagesDir = saveProcessedImagesDir;
        }

        private final void openStreams() throws IOException {
                if (defaultAnalysisOptions.isSaveSummaries()) {
                        summaryWriter = new BufferedWriter(new FileWriter(defaultAnalysisOptions.getSaveSummaryFile()));
                        summaryLineCounter = 0;
                }
        }
        
        private final void closeStreams() {
                if (summaryWriter != null) {
                        try {
                                summaryWriter.flush();
                                summaryWriter.close();
                        }
                        catch (Exception ex) {}
                        summaryWriter = null;
                }
        }
        
        private final void closeSummaryWindow() {
                if(defaultAnalysisOptions.isSaveSummaries()) {
                        Window window = WindowManager.getWindow("Summary");
                        window.setVisible(false);
                        TextWindow txtWin = (TextWindow) window;
                        txtWin.getTextPanel().clear();
                        txtWin.close();
                        txtWin = null;
                        window.dispose();
                        window = null;
                }
        }
        
        /**
         * Perform a cleanup of the summary window every clearSummaries time.
         * This will cause a small visual anomaly of a window appearing and then
         * disappearing very quickly. This is done because calling the method
         * to delete all the lines while the window is still visible does not
         * work. So the window has to physically close and disposed of in memory.
         * A garbage collection is then hinted at to keep memory consumption low.
         */
        private final void cleanUpSummaryWindow() {
                if (summaryLineCounter >= CLEAR_SUMMARY_MAX) {
                        summaryLineCounter = 0;
                        Window window = WindowManager.getWindow("Summary");
                        window.setVisible(false);
                        TextWindow txtWin = (TextWindow) window;
                        txtWin.getTextPanel().clear();
                        txtWin.close();
                        txtWin = null;
                        window.dispose();
                        window = null;
                        System.gc();
                }
        }
        
        public final void processBatch() throws IOException {
                createRoiManager();
                openStreams();
                
                try {
                        File imageFile = null;
                        ImagePlus image = null;
                        ImagePlus imageDup = null;
                        for(int i=0; i < selectedFiles.length; i++) {
                                imageFile = selectedFiles[i];
                                image = openImage(imageFile);
                                
                                if(image != null) {
                                        imageDup = image.duplicate();
                                        processImage(imageFile, image);
                                        analyzeImage(imageFile, image, imageDup);
                                        
                                        image.unlock();
                                        image.flush();
                                        image = null;
                                }
                        }
                
                }
                catch(IOException ioe) {
                        ioe.printStackTrace();
                        IJ.error("I/O Error", "Error while writing to summary file.");
                }
                finally {
                        closeSummaryWindow();
                        destroyRoiManager();
                        closeStreams();
                }
        }
        
        private final void processImage(File imageFile, ImagePlus image) {
                for (ProcessOptions processOption : processOptionsList) {
                        ProcessOption opt = processOption.getOptionKey();
                        switch(opt) {
                                case ENHANCE_LOCAL_CONTRAST: {
                                        enhanceLocalContrast(image, processOption);
                                        if(isSaveProcessedImages) {
                                                IJ.saveAs(image, "jpg", saveProcessedImagesDir+File.separator+imageFile.getName()+"_clahe"); 
                                        }
                                        
                                        break;
                                }
        
                                case SUBTRACT_BACKGROUND: {
                                        subtractBackground(image, processOption);
                                        if(isSaveProcessedImages) {
                                                IJ.saveAs(image, "jpg", saveProcessedImagesDir+File.separator+imageFile.getName()+"_subtract_bg");
                                        }
                                        break;
                                }
        
                                case THRESHOLD: {
                                        adjustThreshold(image, processOption);
                                        if(isSaveProcessedImages) {
                                                IJ.saveAs(image, "jpg", saveProcessedImagesDir+File.separator+imageFile.getName()+"_threshold");
                                        }
                                        break;
                                }
        
                                case REMOVE_OUTLIERS: {
                                        removeOutliers(image, processOption);
                                        if(isSaveProcessedImages) {
                                                IJ.saveAs(image, "jpg", saveProcessedImagesDir+File.separator+imageFile.getName()+"_outliers");
                                        }
                                        break;
                                }
        
                                default: {
                                        break;
                                }
                        }
                }
        }
        
        private final void analyzeImage(File imageFile, ImagePlus image, ImagePlus imageDup) throws IOException {
                for (int i=0; i < analysisOptionsList.size(); i++) {
                        AnalysisOptions analysisOption = analysisOptionsList.get(i);
                        AnalysisOption opt = analysisOption.getOptionKey();
                        switch(opt) {
                                case PARTICLE_ANALYSIS: {
                                        particleAnalysis(imageFile, image, imageDup, analysisOption, i);
                                        break;
                                }
                                
                                default: {
                                        break;
                                }
                        }
                }
        }
        
        private final void particleAnalysis(File imageFile, ImagePlus image, ImagePlus imageDup, AnalysisOptions opt, int index) throws IOException {
                int options = ParticleAnalyzer.EXCLUDE_EDGE_PARTICLES +
                                ParticleAnalyzer.CLEAR_WORKSHEET +
                                ParticleAnalyzer.DISPLAY_SUMMARY +
                                ParticleAnalyzer.ELLIPSE;
                  
                                // Use this if I want to get the outlines with labels
                                // directly from the particle analyzer
                                // ParticleAnalyzer.SHOW_OUTLINES;
                                // analyzer.setHideOutputImage(true);
                                // analyzer.getOutputImage();
                  
                  int measurements = Measurements.AREA +
                                      Measurements.MEAN +
                                      Measurements.STD_DEV +
                                      Measurements.MODE +
                                      Measurements.MEDIAN +
                                      Measurements.MIN_MAX +
                                      Measurements.CENTROID +
                                      Measurements.CENTER_OF_MASS +
                                      Measurements.PERIMETER +
                                      Measurements.FERET +
                                      Measurements.INTEGRATED_DENSITY +
                                      Measurements.AREA_FRACTION +
                                      Measurements.CIRCULARITY;
                  
                  if(defaultAnalysisOptions.isSaveSummaries()) {
                          cleanUpSummaryWindow();
                  }
                  
                  ParticleAnalysisOptions pOptions = (ParticleAnalysisOptions)opt;
                  double minSize = pOptions.getMinParticleSize();
                  double maxSize = 0;
                  
                  if(pOptions.isMaxParticleSizeInfinity()) {
                          maxSize = Double.POSITIVE_INFINITY;
                  }
                  else {
                          maxSize = pOptions.getMaxParticleSize();
                  }
                  
                  double minCirc = pOptions.getMinParticleCirc();
                  double maxCirc = pOptions.getMaxParticleCirc();
                  
                  ResultsTable rt = new ResultsTable();
                  ParticleAnalyzer analyzer = new ParticleAnalyzer(options, measurements, rt, minSize, maxSize, minCirc, maxCirc);
                  ParticleAnalyzer.setRoiManager(roiManager);
                  roiManager.reset();
                  analyzer.analyze(image);
                  
                  if(hasDefaultAnalysisOptions()) {
                          if(defaultAnalysisOptions.isSaveOverlays()) {
                                  Overlay overlay = getOverlay(Color.MAGENTA, 1);
                                  imageDup.setOverlay(overlay);
                                  imageDup.updateImage();
                                  
                                  String overlayDir = defaultAnalysisOptions.getSaveOverlaysDir();
                                  IJ.saveAs(imageDup, "jpg", overlayDir+File.separator+imageFile.getName()+"_overlay_"+index);
                                  
                                  //Remove the overlay so it does not 
                                  //interfere with other analysis methods
                                  //that rely on ROIs
                                  
                                  imageDup.setOverlay(null);
                                  overlay = null;
                          }
                          
                          if(defaultAnalysisOptions.isSaveSummaries()) {
                                  SummaryResults results = new SummaryResults();
                                  Window window = WindowManager.getWindow("Summary");
                                  if (window != null) {
                                          window.setVisible(false);
                                          TextWindow txtWindow = (TextWindow) window;
                                          TextPanel txtPanel = txtWindow.getTextPanel();
                                          String strSummary = txtPanel.getLine(summaryLineCounter);
                                          if (strSummary != null && !strSummary.isEmpty()) {
                                                  results.setSummaryHeadings(txtPanel.getColumnHeadings());
                                                  results.setSummaryLine(strSummary);
                                          }
                                  }
                                  
                                  summaryLineCounter++;
                                  
                                  if(defaultAnalysisOptions.isMeasureRgb()) {
                                          Overlay overlay = getOverlay();
                                          imageDup.setOverlay(overlay);
                                          imageDup.updateImage();
                                          String colorMeasurements = measureRGB(imageDup);
                                          
                                          if(colorMeasurements != null) {
                                                  results.appendResults(colorMeasurements, "R-Mean\tG-Mean\tB-Mean\tR-Std.Dev\tG-Std.Dev\tB-Std.Dev");
                                          }
                                          
                                          //Remove the overlay so it does not 
                                          //interfere with other analysis methods
                                          //that rely on ROIs
                                          
                                          imageDup.setOverlay(null);
                                          colorMeasurements = null;
                                          overlay = null;
                                  }
                                  
                                  if (printSummaryHeadings) {
                                          printSummayHeadings(results);
                                          printSummaryHeadings = false;
                                  }
                                  
                                  printSummary(results);
                                  results = null;
                          }
                  }
        }
        
        private final Overlay getOverlay() {
                return getOverlay(Color.MAGENTA, 1);
        }
        
        private final Overlay getOverlay(Color overlayColor, float overlayWidth) {
                Roi[] rois = roiManager.getRoisAsArray();
                Overlay over = new Overlay();
                for (int j = 0; j < rois.length; j++) {
                        Roi roi = rois[j];
                        roi.setStrokeColor(overlayColor);
                        roi.setStrokeWidth(overlayWidth);
                        over.add(roi);
                }
                
                return over;
        }
        
        private final String measureRGB(ImagePlus imageDup) {
                RGB_Measure_Plus rgbMeasurePlus = new RGB_Measure_Plus();
                RgbMeasure rgbMeasure = null;
                String colorResults = null;
                try {
                        rgbMeasure = rgbMeasurePlus.measureRGB(imageDup);
                        StringBuilder sb = new StringBuilder();
                        sb.append(rgbMeasure.getrMean())
                        .append("\t")
                        .append(rgbMeasure.getgMean())
                        .append("\t")
                        .append(rgbMeasure.getbMean())
                        .append("\t")
                        .append(rgbMeasure.getrStdDev())
                        .append("\t")
                        .append(rgbMeasure.getgStdDev())
                        .append("\t")
                        .append(rgbMeasure.getbStdDev());
                        colorResults = sb.toString();
                        sb = null;
                        rgbMeasure = null;
                }
                catch(Exception ex) {
                        ex.printStackTrace();
                }
                
                rgbMeasurePlus = null;
                
                return colorResults;
        }
        
        private final void enhanceLocalContrast(ImagePlus image, ProcessOptions processOption) {
                ClaheOptions options = (ClaheOptions)processOption;
                
                try {
                        if(options.isFast()) {
                                mpicbg.ij.clahe.Flat.getFastInstance().run(
                                                image,
                                                options.getBlockSize(),
                                                options.getHistogramBins(),
                                                options.getMaximumSlope(),
                                                null,       // mask
                                                false       // composite
                                               );
                        }
                        else {
                                mpicbg.ij.clahe.Flat.getInstance().run(
                                                image,
                                                options.getBlockSize(),
                                                options.getHistogramBins(),
                                                options.getMaximumSlope(),
                                                null,       // mask
                                                false       // composite
                                               );
                        }
                        
                        image.updateImage();
                }
                catch (Exception ex) {
                        IJ.log("Error enhancing local contrast on image: "+image.getFileInfo().fileName);
                        IJ.log("Error: "+ex.getMessage());
                }
        }
        
        private final void removeOutliers(ImagePlus image, ProcessOptions processOption) {
                RemoveOutlierOptions options = (RemoveOutlierOptions)processOption;
                
                RankFilters rankFilters = new RankFilters();
                rankFilters.rank(image.getChannelProcessor(),
                                 options.getRadius(),
                                 RankFilters.OUTLIERS,
                                 options.getWhichOutlier().intValue(),
                                 options.getThreshold());
                
                image.updateImage();
                
                rankFilters = null;
        }
        
        private final void subtractBackground(ImagePlus image, ProcessOptions processOption) {
                SubtractBackgroundOptions options = (SubtractBackgroundOptions)processOption;
                
                ImageConverter ic = new ImageConverter(image);
                ic.convertToGray8();
                image.updateImage();
                
                BackgroundSubtracter bgSubtracter = new BackgroundSubtracter();
                bgSubtracter.rollingBallBackground(image.getChannelProcessor(),
                                                   options.getRollingBallRadius(),
                                                   false,                               //create background -> always false
                                                   options.isLightBackground(),
                                                   false,                               //user paraboloid -> default false
                                                   false,                               //do pre-smooth   -> default false
                                                   false);                              //correct corners -> default false
                image.updateImage();
                
                ic = null;
                bgSubtracter = null;
        }
        
        private final void adjustThreshold(ImagePlus image, ProcessOptions processOption) {
                ThresholdOptions options = (ThresholdOptions)processOption;
                
                String thresholdMethod = options.getThresholdMethod();
                if(options.isDarkBackground()) {
                        thresholdMethod = thresholdMethod+" dark";
                }
                
                IJ.setAutoThreshold(image, thresholdMethod);
                image.updateImage();
                
                //Prefs.blackBackground = false;
                IJ.run(image, "Convert to Mask", "");
                image.updateImage();
        }
        
        private final ImagePlus openImage(File imageFile) {
                ImagePlus image = null;
                try {
                        image = IJ.openImage(imageFile.getAbsolutePath());
                }
                catch(Exception ex) {
                        IJ.log("Error opening image: " + imageFile.getName());
                        IJ.log("Error: "+ex.getMessage());
                }
                
                return image;
        }
        
        private final void printSummary(SummaryResults results) throws IOException {
                String[] summaryLine = results.getSummaryLine();
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < summaryLine.length; j++) {
                        sb.append(summaryLine[j]);
                        sb.append('\t');
                }

                sb.delete(sb.length() - 1, sb.length());

                summaryWriter.write(sb.toString());
                summaryWriter.write(System.getProperty("line.separator"));
                summaryWriter.flush();
                sb = null;
        }
        
        private final void printSummayHeadings(SummaryResults result) throws IOException {
                String[] summaryHeadings = result.getSummaryHeadings();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < summaryHeadings.length; i++) {
                        sb.append(summaryHeadings[i]);
                        sb.append('\t');
                }

                sb.delete(sb.length() - 1, sb.length());

                summaryWriter.write(sb.toString());
                summaryWriter.write(System.getProperty("line.separator"));
                sb = null;
        }
}
