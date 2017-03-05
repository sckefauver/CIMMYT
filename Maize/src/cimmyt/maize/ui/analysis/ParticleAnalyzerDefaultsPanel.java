package cimmyt.maize.ui.analysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import layout.TableLayout;
import cimmyt.maize.options.ParticleAnalysisDefaultOptions;
import cimmyt.maize.ui.tools.FileSave;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Mar 27, 2015
 *
 */
public class ParticleAnalyzerDefaultsPanel extends JPanel {
        
        private static final long serialVersionUID = -8217935811118540382L;
        
        private JPanel saveSummaryPanel = null;
        private JCheckBox saveSummaryCheckBox = null;
        private JTextField saveSummaryField = null;
        private JButton saveSummaryButton = null;
        private File recentSaveSummaryDir = null;
        private File selectedSaveSummaryFile = null;
        
        private JPanel saveOverlaysPanel = null;
        private JCheckBox saveOverlaysCheckBox = null;
        private JTextField saveOverlaysField = null;
        private JButton saveOverlaysButton = null;
        private File selectedSaveOverlayDir = null;
        private File recentSaveOverlayDir = null;
        
        private ParticleAnalysisDefaultOptions analysisDefaultOptions = new ParticleAnalysisDefaultOptions();
        
        public ParticleAnalyzerDefaultsPanel() {
                saveSummaryCheckBox = new JCheckBox("Save Summary:", false);
                saveSummaryCheckBox.setPreferredSize(new Dimension(120, saveSummaryCheckBox.getPreferredSize().height));
                saveSummaryCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveSummaryCheckBox_actionPerformed();
                        }
                });
                
                saveSummaryField = new JTextField(20);
                saveSummaryField.setEditable(false);
                saveSummaryField.setEnabled(false);
                
                saveSummaryButton = new JButton("...");
                saveSummaryButton.setEnabled(false);
                saveSummaryButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                summarySaveButton_actionPerformed();
                        }
                });
                
                saveSummaryPanel = new JPanel();
                saveSummaryPanel.setLayout(new BorderLayout(5, 5));
                saveSummaryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                saveSummaryPanel.add(saveSummaryCheckBox, BorderLayout.WEST);
                saveSummaryPanel.add(saveSummaryField, BorderLayout.CENTER);
                saveSummaryPanel.add(saveSummaryButton, BorderLayout.EAST);
                
                //----------------------------------------------------------------
                
                saveOverlaysCheckBox = new JCheckBox("Save Overlays:", false);
                saveOverlaysCheckBox.setPreferredSize(new Dimension(120, saveOverlaysCheckBox.getPreferredSize().height));
                saveOverlaysCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveOverlaysCheckBox_actionPerformed();
                        }
                });
                
                saveOverlaysField = new JTextField(20);
                saveOverlaysField.setEditable(false);
                saveOverlaysField.setEnabled(false);
                
                saveOverlaysButton = new JButton("...");
                saveOverlaysButton.setEnabled(false);
                saveOverlaysButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveOverlaysButton_actionPerformed();
                        }
                });
                
                saveOverlaysPanel = new JPanel(new BorderLayout(5, 5));
                saveOverlaysPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                saveOverlaysPanel.add(saveOverlaysCheckBox, BorderLayout.WEST);
                saveOverlaysPanel.add(saveOverlaysField, BorderLayout.CENTER);
                saveOverlaysPanel.add(saveOverlaysButton, BorderLayout.EAST);
                
                //----------------------------------------------------------------
                
                double spacer = 5;
                double[][] layoutSize = {
                                {TableLayout.FILL},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED} //2
                };
                
                setBorder(BorderFactory.createTitledBorder("Defaults"));
                setLayout(new TableLayout(layoutSize));
                add(saveSummaryPanel,  "0, 0");
                add(saveOverlaysPanel, "0, 2");
        }
        
        private final void saveSummaryCheckBox_actionPerformed() {
                saveSummaryField.setEnabled(saveSummaryCheckBox.isSelected());
                saveSummaryButton.setEnabled(saveSummaryCheckBox.isSelected());
                if(saveSummaryCheckBox.isSelected()) {
                        saveSummaryField.setBackground(Color.WHITE);
                }
                else {
                        saveSummaryField.setBackground(null);
                }
        }
        
        private final void summarySaveButton_actionPerformed() {
                selectedSaveSummaryFile = FileSave.saveFile("Save As ...", recentSaveSummaryDir, "Summary File", "Summary.xls");
                if(selectedSaveSummaryFile != null) {
                        saveSummaryField.setText(selectedSaveSummaryFile.getAbsolutePath());
                        recentSaveSummaryDir = selectedSaveSummaryFile.getParentFile();
                }
                else {
                        saveSummaryField.setText("");
                }
        }
        
        private final void saveOverlaysCheckBox_actionPerformed() {
                saveOverlaysField.setEnabled(saveOverlaysCheckBox.isSelected());
                saveOverlaysButton.setEnabled(saveOverlaysCheckBox.isSelected());
                if(saveOverlaysCheckBox.isSelected()) {
                        saveOverlaysField.setBackground(Color.WHITE);
                }
                else {
                        saveOverlaysField.setBackground(null);
                }
        }
        
        private final void saveOverlaysButton_actionPerformed() {
                selectedSaveOverlayDir = FileSave.saveFile("Save to Folder", recentSaveOverlayDir, "Overlay save directory");
                if(selectedSaveOverlayDir != null) {
                        saveOverlaysField.setText(selectedSaveOverlayDir.getAbsolutePath());
                        recentSaveOverlayDir = selectedSaveOverlayDir.getParentFile();
                }
                else {
                        saveOverlaysField.setText("");
                }
        }
        
        public final void clear() {
                selectedSaveSummaryFile = null;
                selectedSaveOverlayDir = null;
                saveSummaryField.setText("");
                saveOverlaysField.setText("");
        }
        
        public final ParticleAnalysisDefaultOptions getAnalysisOptions() {
                analysisDefaultOptions.setSaveOverlays(saveOverlaysCheckBox.isSelected());
                analysisDefaultOptions.setSaveSummaries(saveSummaryCheckBox.isSelected());
                
                if(saveOverlaysCheckBox.isSelected()) {
                        analysisDefaultOptions.setSaveOverlaysDir(saveOverlaysField.getText());
                }
                
                if(saveSummaryCheckBox.isSelected()) {
                        analysisDefaultOptions.setSaveSummaryFile(saveSummaryField.getText());
                }
                
                return analysisDefaultOptions;
        }
        
        private boolean overlaySavedState = false;
        private boolean summarySavedState = false;
        
        @Override
        public final void setEnabled(boolean enabled) {
                saveOverlaysCheckBox.setEnabled(overlaySavedState);
                saveSummaryCheckBox.setEnabled(summarySavedState);
                
                if(!enabled) {
                        overlaySavedState = saveOverlaysCheckBox.isSelected();
                        saveOverlaysCheckBox.setEnabled(enabled);
                        saveOverlaysField.setEnabled(enabled);
                        saveOverlaysButton.setEnabled(enabled);
                        
                        summarySavedState = saveSummaryCheckBox.isSelected();
                        saveSummaryCheckBox.setEnabled(enabled);
                        saveSummaryField.setEnabled(enabled);
                        saveSummaryButton.setEnabled(enabled);
                }
                else {
                        saveOverlaysCheckBox.setEnabled(enabled);
                        saveOverlaysField.setEnabled(overlaySavedState);
                        saveOverlaysButton.setEnabled(overlaySavedState);
                        
                        saveSummaryCheckBox.setEnabled(enabled);
                        saveSummaryField.setEnabled(summarySavedState);
                        saveSummaryButton.setEnabled(summarySavedState);
                }
                
                super.setEnabled(enabled);
        }
}
