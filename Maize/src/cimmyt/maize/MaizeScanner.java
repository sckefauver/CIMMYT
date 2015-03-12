package cimmyt.maize;

import ij.IJ;
import ij.plugin.PlugIn;
import javax.swing.UIManager;
import cimmyt.maize.ui.MaizeFrame;
import cimmyt.maize.ui.tools.UITool;

/**
 * 
 * @author George - george.dma@gmail.com Created on: Mar 12, 2015
 *
 */
public class MaizeScanner implements PlugIn {

        @Override
        public void run(String arg) {
                if (IJ.versionLessThan("1.49f")) {
                        IJ.showMessage("This plugin needs to run on ImageJ v1.49f and above");
                }
                else {
                        try {
                                UIManager.setLookAndFeel(UIManager
                                                .getSystemLookAndFeelClassName());
                        }
                        catch (Exception e) {
                                // Will use the standard Look&Feel
                        }

                        MaizeFrame frame = new MaizeFrame();
                        UITool.center(frame);
                        frame.setVisible(true);
                }
        }
}
