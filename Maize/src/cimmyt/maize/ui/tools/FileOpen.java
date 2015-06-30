package cimmyt.maize.ui.tools;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 12, 2015
 *
 */
public final class FileOpen {

        private FileOpen() {

        }

        /**
         * Opens a JFileChooser dialog with customizable parameters. If any are left null then
         * default options will be chosen for you. The default is to allow the selection of
         * any extension. This method will allow the selection of only one file.
         * 
         * 
         * @param title the title of the file chooser dialog
         * @param currentDirectory the directory to start at, if null will start at user.dir
         * @param fileSelectionMode the mode:
         *    JFileChooser.DIRECTORIES_ONLY
         *    JFileChooser.FILES_ONLY
         *    JFileChooser.FILES_AND_DIRECTORIES
         * @param fileFilterDesc the file filter description
         * @param fileFilterExtensions an array of extensions to filter
         * @return File the selected file or null if not selected or canceled
         */
        public static final File getFile(String title, String currentDirectory, int fileSelectionMode, String fileFilterDesc, String ... fileFilterExtensions) {
                JFileChooser chooser = new JFileChooser();

                if (title == null) {
                        chooser.setDialogTitle("File Chooser");
                }
                else {
                        chooser.setDialogTitle(title);
                }

                if (currentDirectory == null) {
                        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                }
                else {
                        chooser.setCurrentDirectory(new File(currentDirectory));
                }

                chooser.setMultiSelectionEnabled(false);
                chooser.setFileSelectionMode(fileSelectionMode);

                if (fileFilterDesc == null) {
                        fileFilterDesc = "All files";
                }

                if (fileFilterExtensions == null) {
                        fileFilterExtensions = new String[] { "*" };
                }

                FileNameExtensionFilter filter = new FileNameExtensionFilter(fileFilterDesc, fileFilterExtensions);
                chooser.setFileFilter(filter);

                int result = chooser.showOpenDialog(null);

                if (result == JFileChooser.CANCEL_OPTION) {
                        return null;
                }

                try {
                        return chooser.getSelectedFile();
                }
                catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Could not select the file.", "Warning!", JOptionPane.WARNING_MESSAGE);
                        return null;
                }
        }

        /**
         * Opens a JFileChooser dialog with customizable parameters. If any are left null then
         * default options will be chosen for you. The default is to allow the selection of
         * any extension. This method will allow the selection of multiple files.
         * 
         * 
         * @param title the title of the file chooser dialog
         * @param currentDirectory the directory to start at, if null will start at user.dir
         * @param fileSelectionMode the mode:
         *  	JFileChooser.DIRECTORIES_ONLY
         *	JFileChooser.FILES_ONLY
         *	JFileChooser.FILES_AND_DIRECTORIES
         * @param fileFilterDesc the file filter description
         * @param fileFilterExtensions an array of extensions to filter
         * @return File[] the selected files or null if nothing selected or canceled
         */
        public static final File[] getFiles(String title, String currentDirectory, int fileSelectionMode, String fileFilterDesc, String... fileFilterExtensions) {
                JFileChooser chooser = new JFileChooser();

                if (title == null) {
                        chooser.setDialogTitle("File Chooser");
                }
                else {
                        chooser.setDialogTitle(title);
                }

                if (currentDirectory == null) {
                        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                }
                else {
                        chooser.setCurrentDirectory(new File(currentDirectory));
                }

                chooser.setMultiSelectionEnabled(true);
                chooser.setFileSelectionMode(fileSelectionMode);

                if (fileFilterDesc == null) {
                        fileFilterDesc = "All files";
                }

                if (fileFilterExtensions == null) {
                        fileFilterExtensions = new String[] { "*" };
                }
                
                FileNameExtensionFilter filter = new FileNameExtensionFilter(fileFilterDesc, fileFilterExtensions);
                chooser.setFileFilter(filter);

                int result = chooser.showOpenDialog(null);

                if (result == JFileChooser.CANCEL_OPTION) {
                        return null;
                }

                try {
                        return chooser.getSelectedFiles();
                }
                catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Could not select the files.", "Warning!", JOptionPane.WARNING_MESSAGE);
                        return null;
                }
        }
}
