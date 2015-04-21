package cimmyt.maize.ui.scanner;

import ij.IJ;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import layout.TableLayout;
import cimmyt.maize.engine.ScannerEngine;
import cimmyt.maize.options.ParticleAnalysisDefaultOptions;
import cimmyt.maize.options.ParticleAnalysisOptions;
import cimmyt.maize.options.RgbMeasureOptions;
import cimmyt.maize.ui.MaizeFrame;
import cimmyt.maize.ui.analysis.ParticleAnalyzerDefaultsPanel;
import cimmyt.maize.ui.analysis.ParticleAnalyzerPanel;
import cimmyt.maize.ui.analysis.RgbMeasurePanel;
import cimmyt.maize.ui.processing.ClahePanel;
import cimmyt.maize.ui.processing.FileSelectPanel;
import cimmyt.maize.ui.processing.ProcessedImagesPanel;
import cimmyt.maize.ui.processing.RemoveOutliersPanel;
import cimmyt.maize.ui.processing.SubtractBackgroundPanel;
import cimmyt.maize.ui.processing.ThresholdPanel;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: May 28, 2015
 *
 */
public class ScannerPanel extends JPanel {
        
        private static final long serialVersionUID = 3227395579779623138L;
        
        private JTabbedPane tabbedPane = null;
        private JPanel processingPanel = null;
        private JPanel analysisPanel = null;
        
        private FileSelectPanel fileSelectPanel = null;
        private ClahePanel clahePanel = null;
        private SubtractBackgroundPanel subtractBackgroundPanel = null;
        private ThresholdPanel thresholdPanel = null;
        private RemoveOutliersPanel removeOutliersPanel = null;
        private ProcessedImagesPanel processedImagesPanel = null;
        private ParticleAnalyzerDefaultsPanel particleAnalyzerDefaultsPanel = null;
        private ParticleAnalyzerPanel particleAnalyzerPanel = null;
        private JScrollPane particleAnalyzerScrollPane = null;
        private RgbMeasurePanel rgbMeasurePanel = null;
        
        private JCheckBox enableClaheCheckBox = null;
        private JCheckBox enableSubtractBackgroundCheckBox = null;
        private JCheckBox enableParticleAnalyzerCheckBox = null;
        private JCheckBox enableThresholdCheckBox = null;
        private JCheckBox enableRemoveOutlisersCheckBox = null;
        private JCheckBox enableParticleAnalyzerDefaultsCheckBox = null;
        private JCheckBox enableRgbMeasureCheckBox = null;
        private JCheckBox enableSaveProcessImagesCheckBox = null;
        
        private JPanel buttonPanel = null;
        private JButton analyzeButton = null;
        
        private ScannerEngine scannerEngine = null;
        
        public ScannerPanel() {
                fileSelectPanel = new FileSelectPanel();
                clahePanel = new ClahePanel();
                subtractBackgroundPanel = new SubtractBackgroundPanel();
                thresholdPanel = new ThresholdPanel();
                removeOutliersPanel = new RemoveOutliersPanel();
                processedImagesPanel = new ProcessedImagesPanel();
                
                particleAnalyzerDefaultsPanel = new ParticleAnalyzerDefaultsPanel();
                particleAnalyzerPanel = new ParticleAnalyzerPanel();
                particleAnalyzerScrollPane = new JScrollPane(particleAnalyzerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                rgbMeasurePanel = new RgbMeasurePanel();
                
                // ---------------------------------------------------
                
                enableClaheCheckBox = new JCheckBox("", true);
                enableClaheCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                clahePanel.setEnabled(((JCheckBox)e.getSource()).isSelected());
                        }
                });
                
                enableSubtractBackgroundCheckBox = new JCheckBox("", true);
                enableSubtractBackgroundCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                subtractBackgroundPanel.setEnabled(((JCheckBox)e.getSource()).isSelected());
                        }
                });
                
                enableParticleAnalyzerCheckBox = new JCheckBox("", true);
                enableParticleAnalyzerCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                particleAnalyzerPanel.setEnabled(((JCheckBox)e.getSource()).isSelected());
                        }
                });
                
                enableThresholdCheckBox = new JCheckBox("", true);
                enableThresholdCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                thresholdPanel.setEnabled(((JCheckBox)e.getSource()).isSelected());
                        }
                });
                
                enableRemoveOutlisersCheckBox = new JCheckBox("", true);
                enableRemoveOutlisersCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                removeOutliersPanel.setEnabled(((JCheckBox)e.getSource()).isSelected());
                        }
                });
                
                enableParticleAnalyzerDefaultsCheckBox = new JCheckBox("", true);
                enableParticleAnalyzerDefaultsCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                particleAnalyzerDefaultsPanel.setEnabled(((JCheckBox)e.getSource()).isSelected());
                        }
                });
                
                enableRgbMeasureCheckBox = new JCheckBox("", false);
                enableRgbMeasureCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                rgbMeasurePanel.setEnabled(((JCheckBox)e.getSource()).isSelected());
                        }
                });
                
                enableSaveProcessImagesCheckBox = new JCheckBox("", false);
                enableSaveProcessImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                processedImagesPanel.setEnabled(((JCheckBox)e.getSource()).isSelected());
                        }
                });
                
                // ---------------------------------------------------
                
                analyzeButton = new JButton("Analyze");
                analyzeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(checkOptions()) {
                                        MaizeFrame.setScannerRunning(true);
                                        analyzeButton_actionPerformed();
                                }
                        }
                });
                
                buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                buttonPanel.add(analyzeButton);
                
                // ---------------------------------------------------
                
                double spacer = 5;
                double[][] processingLayoutSize = {
                                //                   0,         2
                                {TableLayout.PREFERRED, spacer, TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED, //2
                                 spacer,
                                 TableLayout.PREFERRED, //4
                                 spacer,
                                 TableLayout.PREFERRED, //6
                                 spacer,
                                 TableLayout.PREFERRED, //8
                                 spacer,
                                 TableLayout.PREFERRED} //10
                };
                
                processingPanel = new JPanel(new TableLayout(processingLayoutSize));
                processingPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                processingPanel.add(fileSelectPanel,                  "2,  0, 2, 0");
                processingPanel.add(enableClaheCheckBox,              "0,  2, l, t");
                processingPanel.add(clahePanel,                       "2,  2      ");
                processingPanel.add(enableSubtractBackgroundCheckBox, "0,  4, l, t");
                processingPanel.add(subtractBackgroundPanel,          "2,  4      ");
                processingPanel.add(enableThresholdCheckBox,          "0,  6, l, t");
                processingPanel.add(thresholdPanel,                   "2,  6      ");
                processingPanel.add(enableRemoveOutlisersCheckBox,    "0,  8, l, t");
                processingPanel.add(removeOutliersPanel,              "2,  8      ");
                processingPanel.add(enableSaveProcessImagesCheckBox,  "0,  10,l, t");
                processingPanel.add(processedImagesPanel,             "2,  10      ");
                
                // ---------------------------------------------------
                
                double[][] analysisLayoutSize = {
                                //                   0,         2
                                {TableLayout.PREFERRED, spacer, TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED, //2
                                 spacer,
                                 TableLayout.FILL}      //4
                };
                
                analysisPanel = new JPanel(new TableLayout(analysisLayoutSize));
                analysisPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                analysisPanel.add(enableParticleAnalyzerDefaultsCheckBox, "0, 0, l, t");
                analysisPanel.add(particleAnalyzerDefaultsPanel,          "2, 0      ");
                analysisPanel.add(enableRgbMeasureCheckBox,               "0, 2, l, t");
                analysisPanel.add(rgbMeasurePanel,                        "2, 2      ");
                analysisPanel.add(enableParticleAnalyzerCheckBox,         "0, 4, l, t");
                analysisPanel.add(particleAnalyzerScrollPane,             "2, 4      ");
                
                tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
                tabbedPane.addTab("Image Processing", processingPanel);
                tabbedPane.addTab("Image Analysis", analysisPanel);
                
                setLayout(new BorderLayout(5, 5));
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(tabbedPane, BorderLayout.CENTER);
                add(buttonPanel, BorderLayout.SOUTH);
        }
        
        private final boolean checkOptions() {
                if(!enableClaheCheckBox.isSelected() &&
                   !enableSubtractBackgroundCheckBox.isSelected() &&
                   !enableThresholdCheckBox.isSelected() &&
                   !enableRemoveOutlisersCheckBox.isSelected() &&
                   !enableParticleAnalyzerCheckBox.isSelected() &&
                   !enableParticleAnalyzerDefaultsCheckBox.isSelected() &&
                   !enableRgbMeasureCheckBox.isSelected()) {
                        JOptionPane.showMessageDialog(this, "At least one option must be enabled", "Options Check", JOptionPane.INFORMATION_MESSAGE);
                        return false;
                }
                else {
                        if(!fileSelectPanel.isEmpty()) {
                                return true;
                        }
                        else {
                                JOptionPane.showMessageDialog(this, "Image files must be selected", "Options Check", JOptionPane.INFORMATION_MESSAGE);
                                return false;
                        }
                }
        }
        
        private final void analyzeButton_actionPerformed() {
                analyzeButton.setEnabled(false);
                
                scannerEngine = new ScannerEngine();
                scannerEngine.setSelectedFiles(fileSelectPanel.getSelectedFiles());
                
                if(enableClaheCheckBox.isSelected()) {
                        scannerEngine.addProcessOption(clahePanel.getOptions());
                }
                
                if(enableSubtractBackgroundCheckBox.isSelected()) {
                        scannerEngine.addProcessOption(subtractBackgroundPanel.getOptions());
                }
                
                if(enableThresholdCheckBox.isSelected()) {
                        scannerEngine.addProcessOption(thresholdPanel.getOptions());
                }
                
                if(enableRemoveOutlisersCheckBox.isSelected()) {
                        scannerEngine.addProcessOption(removeOutliersPanel.getOptions());
                }
                
                if(enableSaveProcessImagesCheckBox.isSelected()) {
                        scannerEngine.setSaveProcessedImages(true);
                        scannerEngine.setSaveProcessedImagesDir(processedImagesPanel.getSelectedDir());
                }
                
                if(enableParticleAnalyzerCheckBox.isSelected()) {
                        ParticleAnalysisOptions[] options = particleAnalyzerPanel.getAnalysisOptions();
                        for (int i = 0; i < options.length; i++) {
                                scannerEngine.addAnalysisOption(options[i]);
                        }
                }
                
                if(enableParticleAnalyzerDefaultsCheckBox.isSelected()) {
                        ParticleAnalysisDefaultOptions options = particleAnalyzerDefaultsPanel.getAnalysisOptions();
                        
                        if(enableRgbMeasureCheckBox.isSelected()) {
                                RgbMeasureOptions options2 = rgbMeasurePanel.getAnalysisOptions();
                                options.setMeasureRgb(options2.isMeasureRgb());
                        }
                        
                        scannerEngine.setDefaultAnalysisOptions(options);
                }
                
                System.gc();
                
                Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        scannerEngine.processBatch();
                                        EventQueue.invokeLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                        JOptionPane.showMessageDialog(ScannerPanel.this, "Done");
                                                }
                                        });
                                }
                                catch(IOException ioe) {
                                        ioe.printStackTrace();
                                        IJ.error("I/O Error", "There was an error while creating the summary file.");
                                }
                                
                                System.gc();
                                analyzeButton.setEnabled(true);
                                MaizeFrame.setScannerRunning(false);
                        }
                });
                
                t.start();
        }
}
