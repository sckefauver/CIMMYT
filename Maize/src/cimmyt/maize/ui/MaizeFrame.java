package cimmyt.maize.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import layout.TableLayout;
import cimmyt.maize.engine.ScannerEngine;
import cimmyt.maize.options.ParticleAnalysisOptions;
import cimmyt.maize.ui.tools.UITool;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 12, 2015
 *
 */
public class MaizeFrame extends JFrame {

        private static final long serialVersionUID = 6629966401349704225L;
        private JPanel mainPanel = null;
        private FileSelectPanel fileSelectPanel = null;
        private ClahePanel clahePanel = null;
        private SubtractBackgroundPanel subtractBackgroundPanel = null;
        private ThresholdPanel thresholdPanel = null;
        private RemoveOutliersPanel removeOutliersPanel = null;
        private ParticleAnalyzerPanel particleAnalyzerPanel = null;
        private JScrollPane particleAnalyzerScrollPane = null;
        
        private JCheckBox enableClaheCheckBox = null;
        private JCheckBox enableSubtractBackgroundCheckBox = null;
        private JCheckBox enableParticleAnalyzerCheckBox = null;
        private JCheckBox enableThresholdCheckBox = null;
        private JCheckBox enableRemoveOutlisersCheckBox = null;
        
        private JPanel buttonPanel = null;
        private JButton analyzeButton = null;
        
        private ScannerEngine scannerEngine = null;
        
        public MaizeFrame() {
                init();
        }

        private final void init() {
                fileSelectPanel = new FileSelectPanel();
                clahePanel = new ClahePanel();
                subtractBackgroundPanel = new SubtractBackgroundPanel();
                thresholdPanel = new ThresholdPanel();
                removeOutliersPanel = new RemoveOutliersPanel();
                particleAnalyzerPanel = new ParticleAnalyzerPanel();
                particleAnalyzerScrollPane = new JScrollPane(particleAnalyzerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
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
                
                // ---------------------------------------------------
                
                analyzeButton = new JButton("Analyze");
                analyzeButton.setPreferredSize(new Dimension(80, 25));
                analyzeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(checkOptions()) {
                                        analyzeButton_actionPerformed();
                                }
                        }
                });
                
                buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                buttonPanel.add(analyzeButton);
                
                // ---------------------------------------------------
                
                double spacer = 5;
                double[][] layoutSize = {
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
                                 TableLayout.FILL}      //10
                };
                
                mainPanel = new JPanel(new TableLayout(layoutSize));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                mainPanel.add(fileSelectPanel,                  "2,  0, 2, 0");
                mainPanel.add(enableClaheCheckBox,              "0,  2, l, t");
                mainPanel.add(clahePanel,                       "2,  2      ");
                mainPanel.add(enableSubtractBackgroundCheckBox, "0,  4, l, t");
                mainPanel.add(subtractBackgroundPanel,          "2,  4      ");
                mainPanel.add(enableThresholdCheckBox,          "0,  6, l, t");
                mainPanel.add(thresholdPanel,                   "2,  6      ");
                mainPanel.add(enableRemoveOutlisersCheckBox,    "0,  8, l, t");
                mainPanel.add(removeOutliersPanel,              "2,  8     ");
                mainPanel.add(enableParticleAnalyzerCheckBox,   "0, 10, l, t");
                mainPanel.add(particleAnalyzerScrollPane,       "2, 10      ");
                
                setSize(570, 600);
                setTitle("CIMMYT Maize Scanner");
                setLayout(new BorderLayout(5, 5));
                add(mainPanel, BorderLayout.CENTER);
                add(buttonPanel, BorderLayout.SOUTH);
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                                exit();
                        }
                });
        }
        
        private final boolean checkOptions() {
                if(!enableClaheCheckBox.isSelected() &&
                   !enableSubtractBackgroundCheckBox.isSelected() &&
                   !enableThresholdCheckBox.isSelected() &&
                   !enableRemoveOutlisersCheckBox.isSelected() &&
                   !enableParticleAnalyzerCheckBox.isSelected()) {
                        JOptionPane.showMessageDialog(MaizeFrame.this, "At least one option must be enabled", "Options Check", JOptionPane.INFORMATION_MESSAGE);
                        return false;
                }
                else {
                        if(!fileSelectPanel.isEmpty()) {
                                return true;
                        }
                        else {
                                JOptionPane.showMessageDialog(MaizeFrame.this, "Image files must be selected", "Options Check", JOptionPane.INFORMATION_MESSAGE);
                                return false;
                        }
                }
        }
        
        private final void analyzeButton_actionPerformed() {
                analyzeButton.setEnabled(false);
                
                scannerEngine = new ScannerEngine();
                scannerEngine.setParentFrame(this);
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
                
                if(enableParticleAnalyzerCheckBox.isSelected()) {
                        ParticleAnalysisOptions[] options = particleAnalyzerPanel.getOptions();
                        for (int i = 0; i < options.length; i++) {
                                scannerEngine.addAnalysisOption(options[i]);
                        }
                }
                
                System.gc();
                
                Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                scannerEngine.processBatch();
                                System.gc();
                                analyzeButton.setEnabled(true);
                        }
                });
                
                t.start();
        }
        
        private final void exit() {
                int choice = JOptionPane.showConfirmDialog(this, "Do you wish to exit the plugin ?", "Exit Plugin ?", JOptionPane.YES_NO_CANCEL_OPTION);
                if(JOptionPane.YES_OPTION == choice) {
                        setVisible(false);
                        dispose();
                        System.gc();
                }
        }
        
        public static final void main(String... args) {

                try {
                        UIManager.setLookAndFeel(UIManager .getSystemLookAndFeelClassName());
                }
                catch (Exception e) {
                        // Will use the standard Look&Feel
                }

                MaizeFrame frame = new MaizeFrame();
                UITool.center(frame);
                frame.setVisible(true);
        }
}
