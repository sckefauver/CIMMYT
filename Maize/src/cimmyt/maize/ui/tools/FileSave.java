package cimmyt.maize.ui.tools;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JFileChooser;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Mar 20, 2015
 *
 */
public final class FileSave {
        
        private static final boolean IS_MAC = System.getProperty("os.name").indexOf("mac") >= 0;
        
        private FileSave() {

        }
        
        public static final File saveFile(String title, File startDirectory, final String fileDesc) {
                return saveFile(title, startDirectory, fileDesc, null);
        }

        public static final File saveFile(String title, File startDirectory, final String fileDesc, String defaultFileName) {
                File path = null;
                if(IS_MAC) {
                        path = saveFileMac(title, startDirectory, fileDesc, defaultFileName);
                }
                else {
                        path = saveFileOther(title, startDirectory, fileDesc, defaultFileName);
                }
                
                return path;
        }
        
        public static final File saveFileOther(String title, File startDirectory, final String fileDesc, String defaultFileName) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle(title);
                
                File path = null;
                
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
                        path = new File(chooser.getSelectedFile().getAbsolutePath());
                }
                
                return path;
        }
        
        public static final File saveFileMac(String title, File startDirectory, final String fileDesc, String defaultFileName) {
                File path = null;
                
                System.setProperty("apple.awt.fileDialogForDirectories", "true");
                FileDialog d = new FileDialog((Dialog)null, title, FileDialog.SAVE);
                
                if(defaultFileName != null) {
                        d.setFile(defaultFileName);  
                }
                
                d.setFilenameFilter(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                                return dir.isDirectory() || dir.isFile();
                        }
                });
                
                d.setVisible(true);
                String tmp = d.getFile();
                
                if(tmp != null) {
                        path = new File(tmp);
                }
                
                return path;
        }
}
