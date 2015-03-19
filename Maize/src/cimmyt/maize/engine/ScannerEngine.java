package cimmyt.maize.engine;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.BackgroundSubtracter;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.filter.RankFilters;
import ij.plugin.frame.RoiManager;
import ij.process.ImageConverter;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import cimmyt.maize.options.AnalysisOptions;
import cimmyt.maize.options.ClaheOptions;
import cimmyt.maize.options.Options;
import cimmyt.maize.options.ParticleAnalysisOptions;
import cimmyt.maize.options.ProcessOptions;
import cimmyt.maize.options.RemoveOutlierOptions;
import cimmyt.maize.options.SubtractBackgroundOptions;
import cimmyt.maize.options.ThresholdOptions;
import cimmyt.maize.ui.MaizeFrame;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 20, 2015
 *
 */
public class ScannerEngine {

        private List<ProcessOptions> processOptionsList = new ArrayList<ProcessOptions>();
        private List<AnalysisOptions> analysisOptionsList = new ArrayList<AnalysisOptions>();
        private File[] selectedFiles = null;
        @SuppressWarnings("unused")
        private MaizeFrame parentFrame = null;
        private RoiManager roiManager = null;
        
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
        
        public final void setParentFrame(MaizeFrame parentFrame) throws NullPointerException {
                if(parentFrame == null) {
                        throw new NullPointerException("parentFrame cannot be null");
                }
                
                this.parentFrame = parentFrame;
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
        
        public final void processBatch() {
                createRoiManager();
                
                File imageFile = null;
                ImagePlus image = null;
                for(int i=0; i < selectedFiles.length; i++) {
                        imageFile = selectedFiles[i];
                        image = openImage(imageFile);
                        
                        if(image != null) {
                                processImage(imageFile, image);
                                analyzeImage(imageFile, image);
                                
                                image.unlock();
                                image.flush();
                                image = null;
                        }
                }
                
                destroyRoiManager();
        }
        
        private final void processImage(File imageFile, ImagePlus image) {
                for (ProcessOptions processOption : processOptionsList) {
                        Options opt = processOption.getOptionKey();
                        switch(opt) {
                                case ENHANCE_LOCAL_CONTRAST: {
                                        enhanceLocalContrast(image, processOption);
//                                        IJ.saveAs(image, "jpg", "C:\\Users\\George\\fijitest\\clahe_"+imageFile.getName());
                                        break;
                                }
        
                                case SUBTRACT_BACKGROUND: {
                                        subtractBackground(image, processOption);
//                                        IJ.saveAs(image, "jpg", "C:\\Users\\George\\fijitest\\subbg_"+imageFile.getName());
                                        break;
                                }
        
                                case THRESHOLD: {
                                        adjustThreshold(image, processOption);
//                                        IJ.saveAs(image, "jpg", "C:\\Users\\George\\fijitest\\thresh_"+imageFile.getName());
                                        break;
                                }
        
                                case REMOVE_OUTLIERS: {
                                        removeOutliers(image, processOption);
//                                        IJ.saveAs(image, "jpg", "C:\\Users\\George\\fijitest\\outliers_"+imageFile.getName());
                                        break;
                                }
        
                                default: {
                                        break;
                                }
                        }
                }
        }
        
        private final void analyzeImage(File imageFile, ImagePlus image) {
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
                
                for (int i=0; i < analysisOptionsList.size(); i++) {
                        AnalysisOptions analysisOption = analysisOptionsList.get(i);
                        ParticleAnalysisOptions pOptions = (ParticleAnalysisOptions)analysisOption;
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
                        
                        if(pOptions.isSaveOverlays()) {
                                Roi[] rois = roiManager.getRoisAsArray();
                                Overlay over = new Overlay();
                                for (int j = 0; j < rois.length; j++) {
                                        Roi roi = rois[j];
                                        roi.setStrokeColor(Color.MAGENTA);
                                        roi.setStrokeWidth(1);
                                        over.add(roi);
                                }
                                
                                ImagePlus imageDup = image.duplicate();
                                imageDup.setOverlay(over);
                                imageDup.updateAndDraw();
                                
                                String overlayDir = pOptions.getSaveOverlayDir();
                                IJ.saveAs(imageDup, "jpg", overlayDir+File.separator+"overlay_"+i+"_"+imageFile.getName());
                                
                                imageDup.unlock();
                                imageDup.flush();
                                imageDup = null;
                                
                                /*
                                ImagePlus outImg = analyzer.getOutputImage();
                                if(outImg != null) {
                                        String overlayDir = pOptions.getSaveOverlayDir();
                                        IJ.saveAs(outImg, "jpg", overlayDir+File.separator+"overlay_"+i+"_"+imageFile.getName());
                                        
                                        outImg.unlock();
                                        outImg.flush();
                                        outImg = null;
                                }
                                */
                        }
                }
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
}
