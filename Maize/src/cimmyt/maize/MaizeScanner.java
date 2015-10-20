package cimmyt.maize;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ij.IJ;
import ij.plugin.PlugIn;
import javax.swing.UIManager;
import cimmyt.maize.ui.MaizeFrame;
import cimmyt.maize.ui.tools.UITool;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 12, 2015
 *
 */
public class MaizeScanner implements PlugIn {

        private MaizeFrame frame = null;
        
        @Override
        public void run(String arg) {
                if (IJ.versionLessThan("1.49m")) {
                        IJ.showMessage("This plugin needs to run on ImageJ v1.49m and above");
                }
                else {
                        try {
                                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        }
                        catch (Exception e) {
                                // Will use the standard Look&Feel
                        }
                        
                        UITool.showSplashWindow(UITool.getImageIcon("/cimmyt/maize/ui/icons/cimmyt_splash.png"), 2000, new Dimension(660,651), null, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        frame = new MaizeFrame();
                                        UITool.center(frame);
                                        frame.setVisible(true);
                                }
                        });
                }
        }
}
