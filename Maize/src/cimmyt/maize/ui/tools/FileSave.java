package cimmyt.maize.ui.tools;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import cimmyt.maize.MaizeScanner;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
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
        
        public static final void main(String ...strings) {
                File tmp = FileSave.saveFile("Name Results File", new File(System.getProperty("user.dir")), "Results File (*.csv)", "Maize_PlotImages_v"+MaizeScanner.VERSION+"_Results.csv");
                System.out.println("1 "+tmp.getAbsolutePath());
                
                tmp = FileSave.saveFile2("Name Results File", new File(System.getProperty("user.dir")), "Results File (*.csv)", "Maize_PlotImages_v"+MaizeScanner.VERSION+"_Results.csv");
                System.out.println("2 "+tmp.getAbsolutePath());
                
                try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (Exception e) {
                        // Will use the standard Look&Feel
                }
                
                tmp = FileSave.saveFile("Name Results File", new File(System.getProperty("user.dir")), "Results File (*.csv)", "Maize_PlotImages_v"+MaizeScanner.VERSION+"_Results.csv");
                System.out.println("3 "+tmp.getAbsolutePath());
                
                tmp = FileSave.saveFile2("Name Results File", new File(System.getProperty("user.dir")), "Results File (*.csv)", "Maize_PlotImages_v"+MaizeScanner.VERSION+"_Results.csv");
                System.out.println("4 "+tmp.getAbsolutePath());
                
                tmp = FileSave.saveFile3("Name Results File", new File(System.getProperty("user.dir")), "Results File (*.csv)", "Maize_PlotImages_v"+MaizeScanner.VERSION+"_Results.csv");
                System.out.println("5 "+tmp.getAbsolutePath());
                
                System.exit(0);
        }
        
        public static final File saveFile3(String title, File startDirectory, final String fileDesc, String defaultFileName) {
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
                        return new File(tmp);
                }
                else {
                        return null;
                }
        }
        
        public static final File saveFile2(String title, File startDirectory, final String fileDesc, String defaultFileName) {
                
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

                int r = chooser.showDialog(null, "Save 2");

                if (r == JFileChooser.APPROVE_OPTION) {
                        return new File(chooser.getSelectedFile().getAbsolutePath());
                }
                else {
                        return null;
                }
        }
}
