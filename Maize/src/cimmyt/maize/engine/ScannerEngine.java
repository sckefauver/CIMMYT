package cimmyt.maize.engine;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.measure.Calibration;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.BackgroundSubtracter;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.filter.RankFilters;
import ij.plugin.frame.RoiManager;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
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

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 20, 2015
 *
 */
public class ScannerEngine {

        private List<ProcessOptions> processOptionsList = new ArrayList<ProcessOptions>();
        private List<AnalysisOptions> analysisOptionsList = new ArrayList<AnalysisOptions>();
        private ParticleAnalysisDefaultOptions defaultAnalysisOptions = null;
        private File[] selectedFiles = null;
        private RoiManager roiManager = null;
        
        private BufferedWriter summaryWriter = null;
        private boolean isSaveProcessedImages = false;
        private String saveProcessedImagesDir = null;
        private boolean headingsPrinted = false;
        
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
        
        //TODO move these somewhere more useful
        
        private static final MathContext MC = new MathContext(4);
        
        private static final int ANALYZER_OPTIONS = ParticleAnalyzer.EXCLUDE_EDGE_PARTICLES +
                                                    ParticleAnalyzer.CLEAR_WORKSHEET +
                                                    ParticleAnalyzer.ELLIPSE;
        
        private static final int ANALYZER_MEASUREMENTS =
                                 Measurements.AREA +
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
        
        private static final int[] RESULTS_COLUMNS = {
                                   ResultsTable.AREA,
                                   ResultsTable.MEAN,
                                   ResultsTable.MODE,
                                   ResultsTable.MEDIAN,
                                   ResultsTable.PERIMETER,
                                   ResultsTable.FERET,
                                   ResultsTable.FERET_X,
                                   ResultsTable.FERET_Y,
                                   ResultsTable.FERET_ANGLE,
                                   ResultsTable.MIN_FERET,
                                   ResultsTable.INTEGRATED_DENSITY,
                                   ResultsTable.CIRCULARITY,
                                   ResultsTable.SOLIDITY
        };
        
        private static final String[] SUMMARY_HEADINGS = {
                "Total area",
                "%Area",
                "Average Size",
                "Mean",
                "Mode",
                "Median",
                "Perim.",
                "Feret",
                "FeretX",
                "FeretY",
                "FeretAngle",
                "MinFeret",
                "IntDen",
                "Circ.",
                "Solidity",
        };
        
        private static final String[] SUMMARY_RGB_HEADINGS = {
                "R-Mean",
                "G-Mean",
                "B-Mean",
                "R-StdDev",
                "G-StdDev",
                "B-StdDev",
                "R-Area",
                "G-Area",
                "B-Area",
        };
        
        private final void particleAnalysis(File imageFile, ImagePlus image, ImagePlus imageDup, AnalysisOptions opt, int index) throws IOException {
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
                  ParticleAnalyzer analyzer = new ParticleAnalyzer(ANALYZER_OPTIONS, ANALYZER_MEASUREMENTS, rt, minSize, maxSize, minCirc, maxCirc);
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
                                  float[] summary = calculateSummary(rt, image);
                                  
                                  if(defaultAnalysisOptions.isMeasureRgb()) {
                                          Overlay overlay = getOverlay();
                                          imageDup.setOverlay(overlay);
                                          imageDup.updateImage();
                                          RgbMeasure rgbMeasure = measureRGB(imageDup);
                                          
                                          if(rgbMeasure != null) {
                                                  summary[RESULTS_COLUMNS.length+2] = new BigDecimal(rgbMeasure.getrMean()).round(MC).floatValue();
                                                  summary[RESULTS_COLUMNS.length+3] = new BigDecimal(rgbMeasure.getgMean()).round(MC).floatValue();
                                                  summary[RESULTS_COLUMNS.length+4] = new BigDecimal(rgbMeasure.getbMean()).round(MC).floatValue();
                                                  
                                                  summary[RESULTS_COLUMNS.length+5] = new BigDecimal(rgbMeasure.getrStdDev()).round(MC).floatValue();
                                                  summary[RESULTS_COLUMNS.length+6] = new BigDecimal(rgbMeasure.getgStdDev()).round(MC).floatValue();
                                                  summary[RESULTS_COLUMNS.length+7] = new BigDecimal(rgbMeasure.getbStdDev()).round(MC).floatValue();
                                                  
                                                  summary[RESULTS_COLUMNS.length+8] = new BigDecimal(rgbMeasure.getrArea()).round(MC).floatValue();
                                                  summary[RESULTS_COLUMNS.length+9] = new BigDecimal(rgbMeasure.getgArea()).round(MC).floatValue();
                                                  summary[RESULTS_COLUMNS.length+10] = new BigDecimal(rgbMeasure.getbArea()).round(MC).floatValue();
                                          }
                                          
                                          //Remove the overlay so it does not 
                                          //interfere with other analysis methods
                                          //that rely on ROIs
                                          
                                          imageDup.setOverlay(null);
                                          rgbMeasure = null;
                                          overlay = null;
                                  }
                                  
                                  printSummary(imageFile.getName(), summary);
                          }
                  }
        }
        
        private final float[] calculateSummary(ResultsTable rt, ImagePlus image) {
                float[] summaries = null;
                if(defaultAnalysisOptions.isMeasureRgb()) {
                        summaries = new float[RESULTS_COLUMNS.length+11]; //9 for RGB and 2 for area & %area
                }
                else {
                        summaries = new float[RESULTS_COLUMNS.length+2];
                }
                
                float areaSum = calculateSum(rt.getColumn(RESULTS_COLUMNS[ResultsTable.AREA]));
                
                Calibration calibration = image.getCalibration();
                ImageProcessor ip = image.getProcessor();
                Rectangle r = ip.getRoi();
                ImageProcessor mask = ip.getMask();
                double totalArea = 0;
                if(mask != null) {
                        totalArea = ImageStatistics.getStatistics(ip, Measurements.AREA, calibration).area;
                }
                else {
                        totalArea = r.width*calibration.pixelWidth*r.height*calibration.pixelHeight;
                }
                
                float pctArea = areaSum*100.0f/(float)totalArea;
                
                summaries[0] = areaSum;
                summaries[1] = new BigDecimal(pctArea).round(MC).floatValue();
                
                for(int i=0; i < RESULTS_COLUMNS.length; i++) {
                        float[] columnData = rt.getColumn(RESULTS_COLUMNS[i]);
                        float sumAvg = calculateSumAverage(columnData);
                        summaries[i+2] = new BigDecimal(sumAvg).round(MC).floatValue();
                }
                
                return summaries;
        }
        
        private final float calculateSumAverage(float[] data) {
                float sumAvg = 0;
                for(int i=0; i < data.length; i++) {
                        sumAvg = sumAvg + data[i];
                }
                
                sumAvg = sumAvg /  data.length;
                return sumAvg;
        }
        
        private final float calculateSum(float[] data) {
                float sum = 0;
                for(int i=0; i < data.length; i++) {
                        sum = sum + data[i];
                }
                
                return sum;
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
        
        private final RgbMeasure measureRGB(ImagePlus imageDup) {
                RGB_Measure_Plus rgbMeasurePlus = new RGB_Measure_Plus();
                RgbMeasure rgbMeasure = null;
                
                try {
                        rgbMeasure = rgbMeasurePlus.measureRGB(imageDup);
                }
                catch(Exception ex) {
                        ex.printStackTrace();
                }
                
                rgbMeasurePlus = null;
                
                return rgbMeasure;
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
        
        private final void printSummary(String imageName, float[] summary) throws IOException {
                StringBuilder sb = null;
                
                if(!headingsPrinted) {
                        sb = new StringBuilder();
                        sb.append("Image Name\t");
                        
                        for(int i=0; i < SUMMARY_HEADINGS.length; i++) {
                                sb.append(SUMMARY_HEADINGS[i]);
                                sb.append('\t');
                        }
                        
                        if(defaultAnalysisOptions.isMeasureRgb()) {
                                for(int i=0; i < SUMMARY_RGB_HEADINGS.length; i++) {
                                        sb.append(SUMMARY_RGB_HEADINGS[i]);
                                        sb.append('\t');
                                }
                        }
                        
                        sb.delete(sb.length() - 1, sb.length());
                        summaryWriter.write(sb.toString());
                        summaryWriter.write(System.getProperty("line.separator"));
                        
                        headingsPrinted = true;
                }
                
                sb = new StringBuilder();
                sb.append(imageName).append('\t');
                for(int i=0; i < summary.length; i++) {
                        sb.append(summary[i]);
                        sb.append('\t');
                }
                
                sb.delete(sb.length() - 1, sb.length());
                summaryWriter.write(sb.toString());
                summaryWriter.write(System.getProperty("line.separator"));
                summaryWriter.flush();
                sb = null;
        }
}
