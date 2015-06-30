package cimmyt.maize.ui.processing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import cimmyt.maize.ui.tools.FileOpen;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 12, 2015
 *
 */
public class FileSelectPanel extends JPanel {
        
        private static final long serialVersionUID = -7546340004079744186L;
        
        private JLabel dirLabel = null;
        private JTextField dirField = null;
        private JButton dirOpenButton = null;
        private File[] selectedFiles = null;
        private String recentDir = null;

        public FileSelectPanel() {
                dirLabel = new JLabel("Images:");
                dirLabel.setHorizontalAlignment(JLabel.RIGHT);
                dirLabel.setPreferredSize(new Dimension(60, 22));
                
                dirField = new JTextField(20);
                dirField.setEditable(false);
                dirField.setBackground(Color.WHITE);
                
                dirOpenButton = new JButton("...");
                dirOpenButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                dirOpenButton_actionPerformed();
                        }
                });
                
                setLayout(new BorderLayout(5, 5));
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(dirLabel, BorderLayout.WEST);
                add(dirField, BorderLayout.CENTER);
                add(dirOpenButton, BorderLayout.EAST);
        }
        
        private final void dirOpenButton_actionPerformed() {
                selectedFiles = FileOpen.getFiles("Select the maize images", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.FILES_ONLY , "Image Files", "jpg","jpeg");
                if(selectedFiles != null) {
                        recentDir = selectedFiles[0].getParent();
                        dirField.setText(selectedFiles.length+" files in "+recentDir);
                }
        }
        
        public final File[] getSelectedFiles() {
                return selectedFiles;
        }
        
        public final boolean isEmpty() {
                return selectedFiles == null;
        }
        
        public final void clear() {
                selectedFiles = null;
                dirField.setText("");
        }
}
