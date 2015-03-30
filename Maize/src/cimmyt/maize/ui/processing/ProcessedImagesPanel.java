package cimmyt.maize.ui.processing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cimmyt.maize.ui.tools.FileSave;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 30, 2015
 *
 */
public class ProcessedImagesPanel extends JPanel {
        
        private static final long serialVersionUID = 8412891058372393453L;
        
        private JCheckBox saveProcImagesCheckBox = null;
        private JTextField saveProcImagesField = null;
        private JButton saveProcImagesButton = null;
        private File selectedSaveProcImagesDir = null;
        private File recentSaveProcImagesDir = null;
        
        public ProcessedImagesPanel() {
                saveProcImagesCheckBox = new JCheckBox("Save Proccessed Images:", false);
                saveProcImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveProcImagesCheckBox_actionPerformed();
                        }
                });
                
                saveProcImagesField = new JTextField(20);
                saveProcImagesField.setEditable(false);
                saveProcImagesField.setEnabled(false);
                
                saveProcImagesButton = new JButton("...");
                saveProcImagesButton.setEnabled(false);
                saveProcImagesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveProcImagesButton_actionPerformed();
                        }
                });
                
                setLayout(new BorderLayout(5, 5));
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(saveProcImagesCheckBox, BorderLayout.WEST);
                add(saveProcImagesField, BorderLayout.CENTER);
                add(saveProcImagesButton, BorderLayout.EAST);
                
                setEnabled(false);
        }
        
        private final void saveProcImagesCheckBox_actionPerformed() {
                saveProcImagesField.setEnabled(saveProcImagesCheckBox.isSelected());
                saveProcImagesButton.setEnabled(saveProcImagesCheckBox.isSelected());
                if(saveProcImagesCheckBox.isSelected()) {
                        saveProcImagesField.setBackground(Color.WHITE);
                }
                else {
                        saveProcImagesField.setBackground(null);
                }
        }
        
        private final void saveProcImagesButton_actionPerformed() {
                selectedSaveProcImagesDir = FileSave.saveFile("Save to Folder", recentSaveProcImagesDir, "Processed Images save directory");
                if(selectedSaveProcImagesDir != null) {
                        saveProcImagesField.setText(selectedSaveProcImagesDir.getAbsolutePath());
                        recentSaveProcImagesDir = selectedSaveProcImagesDir.getParentFile();
                }
                else {
                        saveProcImagesField.setText("");
                }
        }
        
        public final String getSelectedDir() {
                return saveProcImagesField.getText();
        }
        
        private boolean procImagesSavedState = false;
        
        @Override
        public final void setEnabled(boolean enabled) {
                saveProcImagesCheckBox.setEnabled(procImagesSavedState);
                
                if(!enabled) {
                        procImagesSavedState = saveProcImagesCheckBox.isSelected();
                        saveProcImagesCheckBox.setEnabled(enabled);
                        saveProcImagesField.setEnabled(enabled);
                        saveProcImagesButton.setEnabled(enabled);
                }
                else {
                        saveProcImagesCheckBox.setEnabled(enabled);
                        saveProcImagesField.setEnabled(procImagesSavedState);
                        saveProcImagesButton.setEnabled(procImagesSavedState);
                }
                
                super.setEnabled(enabled);
        }
}
