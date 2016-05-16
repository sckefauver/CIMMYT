package cimmyt.maize.ui.breedpix;

import ij.IJ;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import layout.TableLayout;
import cimmyt.maize.engine.breedpix.BreedPixResult;
import cimmyt.maize.engine.breedpix.PicVIOperation;
import cimmyt.maize.engine.breedpix.PixelMask;
import cimmyt.maize.ui.MaizeFrame;
import cimmyt.maize.ui.tools.FileOpen;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Sep 10, 2015
 */
public class BreedPixPanel extends JPanel {

        private static final long serialVersionUID = -3745899063838683488L;
        
        private JLabel batchInputLabel = null;
        private JTextField batchInputField = null;
        private JButton batchInputButton = null;
        
        private JCheckBox saveGaImageCheckBox = null;
        private JCheckBox saveGgaImageCheckBox = null;
        private JTextField saveImageField = null;
        private JButton saveImageButton = null;
        
        private JLabel resultsFileLabel = null;
        private JTextField resultsFileField = null;
        private JButton resultsFileButton = null;
        
        private JPanel optionsPanel = null;
        
        private JPanel buttonPanel = null;
        private JButton runButton = null;
        private JProgressBar progressBar = null;
        
        private String recentDir = null;
        private File batchInputDir = null;
        private File saveImageDir = null;
        private File saveResultsFile = null;
        
        public BreedPixPanel() {
                batchInputLabel = new JLabel("Batch Inputs:");
                batchInputLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                batchInputField = new JTextField(20);
                batchInputField.setEditable(false);
                batchInputField.setBackground(Color.WHITE);
                
                batchInputButton = new JButton("...");
                batchInputButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                batchInputButton_actionPerformed();
                        }
                });
                
              //----------------------------------------------------------------
                
                saveGaImageCheckBox = new JCheckBox("Save GA", false);
                saveGaImageCheckBox.setFocusable(false);
                saveGaImageCheckBox.setSelected(false);
                saveGaImageCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveGaImageCheckBox_actionPerformed();
                        }
                });
                
                saveGgaImageCheckBox = new JCheckBox("GGA Images?", false);
                saveGgaImageCheckBox.setFocusable(false);
                saveGgaImageCheckBox.setSelected(false);
                saveGgaImageCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveGgaImageCheckBox_actionPerformed();
                        }
                });
                
                saveImageField = new JTextField(20);
                saveImageField.setEditable(false);
                saveImageField.setBackground(null);
                
                saveImageButton = new JButton("...");
                saveImageButton.setEnabled(false);
                saveImageButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveImageButton_actionPerformed();
                        }
                });
                
              //----------------------------------------------------------------
                
                resultsFileLabel = new JLabel("Results File:");
                resultsFileLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                resultsFileField = new JTextField(20);
                resultsFileField.setEditable(false);
                resultsFileField.setBackground(Color.WHITE);
                
                resultsFileButton = new JButton("...");
                resultsFileButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                resultsFileButton_actionPerformed();  
                        }
                });
                
              //----------------------------------------------------------------
                
                double spacer = 5;
                double[][] layoutSize = {
                                //                   0,                             2,                        4,                             6
                                {TableLayout.PREFERRED, spacer, TableLayout.PREFERRED, spacer, TableLayout.FILL, spacer, TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED, //2
                                 spacer,
                                 TableLayout.PREFERRED, //4
                                 spacer,
                                 TableLayout.PREFERRED, //6
                                 spacer,
                                 TableLayout.PREFERRED  //8
                                }
                };
                
                optionsPanel = new JPanel();
                optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
                optionsPanel.setLayout(new TableLayout(layoutSize));
                optionsPanel.add(batchInputLabel,      "0, 0");
                optionsPanel.add(batchInputField,      "2, 0, 4");
                optionsPanel.add(batchInputButton,     "6, 0");
                optionsPanel.add(saveGaImageCheckBox,  "0, 2");
                optionsPanel.add(saveGgaImageCheckBox, "2, 2");
                optionsPanel.add(saveImageField,       "4, 2");
                optionsPanel.add(saveImageButton,      "6, 2");
                optionsPanel.add(resultsFileLabel,     "0, 4");
                optionsPanel.add(resultsFileField,     "2, 4, 4");
                optionsPanel.add(resultsFileButton,    "6, 4");
                
                runButton = new JButton("Run BreedPix");
                runButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                runButton_actionPerformed();
                        }
                });
                
                progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
                progressBar.setStringPainted(true);
                progressBar.setString("Ready");
                progressBar.setIndeterminate(false);
                
                buttonPanel = new JPanel(new BorderLayout(5, 5));
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                buttonPanel.add(runButton, BorderLayout.EAST);
                buttonPanel.add(progressBar, BorderLayout.CENTER);
                
                // ---------------------------------------------------
                
                setLayout(new BorderLayout(5, 5));
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(optionsPanel, BorderLayout.NORTH);
                add(buttonPanel, BorderLayout.SOUTH);
        }
        
        private final void runButton_actionPerformed() {
                if(batchInputDir == null) {
                        JOptionPane.showMessageDialog(this, "Select a folder to read the input images", "Empty Batch Input", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                if(saveResultsFile == null) {
                        JOptionPane.showMessageDialog(this, "Select a results file name and location to store the results of the batch process", "Empty Results File", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                Thread breedPixThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                EventQueue.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                                runButton.setEnabled(false);
                                                progressBar.setIndeterminate(true);
                                                progressBar.setString("Running BreedPix ...");
                                                MaizeFrame.setBreedPixRunning(true);
                                        }
                                });
                                
                                process();
                                System.gc();
                                
                                EventQueue.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                                progressBar.setIndeterminate(false);
                                                progressBar.setString("Ready");
                                                runButton.setEnabled(true);
                                                MaizeFrame.setBreedPixRunning(false);
                                        }
                                });
                        }
                });
                
                breedPixThread.start();
        }
        
        private final void batchInputButton_actionPerformed() {
                batchInputDir = FileOpen.getFile("Select batch input folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY, "Batch Image Folder", (String[]) null);
                if (batchInputDir != null) {
                        recentDir = batchInputDir.getAbsolutePath();
                        batchInputField.setText(batchInputDir.getAbsolutePath());
                }
        }
        
        private final void process() {
                File[] imageFiles = batchInputDir.listFiles();
                PicVIOperation picViOperation = new PicVIOperation();
                File imageFile = null;
                String ext = null;
                String fileName = null;
                String imagePath = null;
                if(saveImageDir != null) {
                        imagePath = saveImageDir.getAbsolutePath();
                }
                
                StringBuilder resultBuilder = new StringBuilder();
                resultBuilder.append("Image Name,Intensity, Hue, Saturation, Lightness, a*, b*, u*, v*, GA, GGA");
                resultBuilder.append(System.getProperty("line.separator"));
                
                for(int i=0; i < imageFiles.length; i++) {
                        imageFile = imageFiles[i];
                        fileName = imageFile.getName();
                        ext = fileName.substring(fileName.lastIndexOf('.')+1).toLowerCase();
                        
                        switch(ext) {
                                case "jpg":
                                case "jpeg":
                                case "png":
                                case "tif":
                                case "tiff": {
                                        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                                        
                                        BufferedImage image = openImage(imageFile);
                                        BufferedImage scaledRenderedImage = reduceToMaxSize(image, 1024*768);
                                        BreedPixResult result = picViOperation.execute(scaledRenderedImage);
                                        
                                        if( result != null) {
                                                if(saveGaImageCheckBox.isSelected()) {
                                                        PixelMask gaRoi = result.getGa_roi();
                                                        BufferedImage gaRoiImage = paintBWNotROI(scaledRenderedImage, gaRoi, fileName);
                                                        
                                                        try {
                                                                ImageIO.write(gaRoiImage, "jpg", new File(imagePath+File.separator+fileName+"_GA.JPG"));
                                                        }
                                                        catch(IOException ioe) {
                                                                IJ.log("Error saving GA ROI image: " + fileName);
                                                                IJ.log("Error: "+ioe.getMessage());
                                                        }
                                                        finally {
                                                                gaRoi = null;
                                                                gaRoiImage = null;
                                                        }
                                                }
                                                
                                                if(saveGgaImageCheckBox.isSelected()) {
                                                        PixelMask ggaRoi = result.getGga_roi();
                                                        BufferedImage ggaRoiImage = paintBWNotROI(scaledRenderedImage, ggaRoi, fileName);
                                                        
                                                        try {
                                                                ImageIO.write(ggaRoiImage, "jpg", new File(imagePath+File.separator+fileName+"_GGA.JPG"));
                                                        }
                                                        catch(IOException ioe) {
                                                                IJ.log("Error saving GGA ROI image: " + fileName);
                                                                IJ.log("Error: "+ioe.getMessage());
                                                        }
                                                        finally {
                                                                ggaRoi = null;
                                                                ggaRoiImage = null;
                                                        }
                                                }
                                                
                                                resultBuilder.append(fileName).append('.').append(ext);
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getIhs_i());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getIhs_h());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getIhs_s());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getLab_l());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getLab_a());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getLab_b());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getLuv_u());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getLuv_v());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getGa());
                                                resultBuilder.append(',');
                                                resultBuilder.append(result.getGga());
                                                resultBuilder.append(System.getProperty("line.separator"));
                                        }
                                        
                                        scaledRenderedImage = null;
                                        image = null;
                                        fileName = null;
                                        imageFile = null;
                                        break;
                                }
                        }
                }
                
                printResults(resultBuilder.toString());
        }
        
        private final void printResults(String results) {
                try {
                        FileWriter fw = new FileWriter(saveResultsFile);
                        fw.write(results);
                        fw.flush();
                        fw.close();
                        fw = null;
                }
                catch(IOException ioe) {
                        IJ.log("Error saving results file: " + saveResultsFile.getAbsoluteFile());
                        IJ.log("Error: "+ioe.getMessage());
                        IJ.error("I/O Error", "There was an error while saving the results file.");
                }
        }
        
        private final BufferedImage openImage(File imageFile) {
                BufferedImage image = null;
                try {
                        image = IJ.openImage(imageFile.getAbsolutePath()).getBufferedImage();
                }
                catch(Exception ex) {
                        IJ.log("Error opening image: " + imageFile.getName());
                        IJ.log("Error: "+ex.getMessage());
                }
                
                return image;
        }
        
        private final void saveGaImageCheckBox_actionPerformed() {
                if(saveGaImageCheckBox.isSelected()) {
                        saveImageButton.setEnabled(true);
                        saveImageField.setBackground(Color.WHITE);
                }
                else {
                        if(!saveGgaImageCheckBox.isSelected()) {
                                saveImageButton.setEnabled(false);
                                saveImageField.setBackground(null);
                        }
                }
        }
        
        private final void saveGgaImageCheckBox_actionPerformed() {
                if(saveGgaImageCheckBox.isSelected()) {
                        saveImageButton.setEnabled(true);
                        saveImageField.setBackground(Color.WHITE);
                }
                else {
                        if(!saveGaImageCheckBox.isSelected()) {
                                saveImageButton.setEnabled(false);
                                saveImageField.setBackground(null);
                        }
                }
        }
        
        private final void saveImageButton_actionPerformed() {
                saveImageDir = FileOpen.getFile("Select GA/GGA images folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY , "GA/GGA Images Folder", (String[])null);
                if(saveImageDir != null) {
                        recentDir = saveImageDir.getAbsolutePath();
                        saveImageField.setText(saveImageDir.getAbsolutePath());
                }
        }
        
        private final void resultsFileButton_actionPerformed() {
                saveResultsFile = FileOpen.getFile("Name Results File", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.FILES_AND_DIRECTORIES , "Results File (*.xls)", "xls");
                if(saveResultsFile != null) {
                        recentDir = saveResultsFile.getParentFile().getAbsolutePath();
                        
                        if(!saveResultsFile.getName().endsWith(".xls")) {
                                String tmp = saveResultsFile.getAbsolutePath();
                                saveResultsFile = new File(tmp+".xls");
                        }
                        
                        resultsFileField.setText(saveResultsFile.getAbsolutePath());
                }
        }
        
        /**
         * Copyright 2012 Jaume Casadesus
         * <p>
         * This code block can not be copied and/or distributed without the express
         * permission of Jaume Casadesus
         * 
         * @author Jaume Casadesus (jaume.casadesus@irta.cat)
         */
        public BufferedImage reduceToMaxSize(BufferedImage image, int maxPixels) {
                int size = image.getHeight() * image.getWidth();

                double scale = 1.0D;
                BufferedImage rimg;
                if (size > maxPixels) {
                        scale = Math.sqrt(maxPixels * 1.0D / size);
                        rimg = scaleImage(image, scale);
                }
                else {
                        rimg = image;
                }
                return rimg;
        }
        
        /**
         * Copyright 2012 Jaume Casadesus
         * <p>
         * This code block can not be copied and/or distributed without the express
         * permission of Jaume Casadesus
         * 
         * @author Jaume Casadesus (jaume.casadesus@irta.cat)
         */
        public BufferedImage scaleImage(BufferedImage image, double scale) {
                return scaleImage(image, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
        }
        
        /**
         * Copyright 2012 Jaume Casadesus
         * <p>
         * This code block can not be copied and/or distributed without the express
         * permission of Jaume Casadesus
         * 
         * @author Jaume Casadesus (jaume.casadesus@irta.cat)
         */
        public BufferedImage scaleImage(BufferedImage image, int targetWidth, int targetHeight) {
                int imgType = image.getType();

                BufferedImage newImg = new BufferedImage(targetWidth, targetHeight, imgType);
                Graphics2D g2 = newImg.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(image, 0, 0, targetWidth, targetHeight, 0, 0, image.getWidth(), image.getHeight(), null);
                return newImg;
        }
        
        /**
         * Copyright 2012 Jaume Casadesus
         * <p>
         * This code block can not be copied and/or distributed without the express
         * permission of Jaume Casadesus
         * 
         * @author Jaume Casadesus (jaume.casadesus@irta.cat)
         */
        public static BufferedImage paintBWNotROI(RenderedImage img, PixelMask roi, String imageName) {
                BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                try {
                        // Create the scale operation

                        int numXTiles = img.getNumXTiles();
                        int numYTiles = img.getNumYTiles();
                        WritableRaster wrRaster = null;
                        // sets the value for each pixel
                        int[] pixel = new int[3];

                        for (int i = 0; i < numXTiles; i++) {
                                for (int j = 0; j < numYTiles; j++) {
                                        wrRaster = img.getTile(i, j).createCompatibleWritableRaster();
                                        wrRaster = img.copyData(wrRaster);
                                        int minX = wrRaster.getMinX();
                                        int maxX = minX + wrRaster.getWidth();
                                        int minY = wrRaster.getMinY();
                                        int maxY = minY + wrRaster.getHeight();

                                        for (int x = minX; x < maxX; x++) {
                                                for (int y = minY; y < maxY; y++) {
                                                        if (!roi.contains(x, y)) {
                                                                pixel = wrRaster.getPixel(x, y, pixel);
                                                                pixel[0] = (pixel[0] + pixel[1] + pixel[2]) / 6;
                                                                pixel[1] = pixel[0];
                                                                pixel[2] = pixel[0];
                                                                wrRaster.setPixel(x, y, pixel);
                                                        }
                                                }
                                        }
                                        bi.setData(wrRaster);
                                }
                        }
                }
                catch (Exception ex) {
                        IJ.log("Error rendering ROI for image: " + imageName);
                        IJ.log("Error: "+ex.getMessage());
                }
                
                return bi;
        }
}
