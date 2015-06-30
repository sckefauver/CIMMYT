package cimmyt.maize.ui.tools;

import java.io.File;
import javax.swing.JFileChooser;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 20, 2015
 *
 */
public final class FileSave {
        
        private FileSave() {

        }

        public static final File saveFile(String title, File startDirectory, final String fileDesc) {
                return saveFile(title, startDirectory, fileDesc, null);
        }
        
        public static final File saveFile(String title, File startDirectory, final String fileDesc, String defaultFileName) {
                
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle(title);
                
                if(defaultFileName != null) {
                        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        chooser.setSelectedFile(new File(chooser.getCurrentDirectory().getAbsolutePath()+File.separator+defaultFileName));
                }
                else {
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                }

                if (startDirectory != null) {
                        chooser.setCurrentDirectory(startDirectory);
                }
                else {
                        chooser.setCurrentDirectory(new File(System.getProperties().getProperty("user.home")));
                }

                chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                        @Override
                        public boolean accept(File f) {
                                return f.isDirectory() || f.isFile();
                        }

                        @Override
                        public String getDescription() {
                                return fileDesc;
                        }
                });

                int r = chooser.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                        return new File(chooser.getSelectedFile().getAbsolutePath());
                }
                else {
                        return null;
                }
        }
}
