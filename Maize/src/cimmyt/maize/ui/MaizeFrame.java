package cimmyt.maize.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import cimmyt.maize.ui.canopymacro.CanopyLevelMacroPanel;
import cimmyt.maize.ui.scanner.ScannerPanel;
import cimmyt.maize.ui.tools.UITool;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 12, 2015
 *
 */
public class MaizeFrame extends JFrame {

        private static final long serialVersionUID = 6629966401349704225L;
        
        private JTabbedPane tabbedPane = null;
        private ScannerPanel scannerPanel = null;
        private CanopyLevelMacroPanel canopyMacroPanel = null;

        private static boolean scannerRunning = false;
        private static boolean macrosRunning = false;
        
        public MaizeFrame() {
                init();
        }

        private final void init() {
                scannerPanel = new ScannerPanel();
                canopyMacroPanel = new CanopyLevelMacroPanel();
                
                tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
                tabbedPane.addTab("Scanner", scannerPanel);
                tabbedPane.addTab("Canopy Macros", canopyMacroPanel);
                
                // ---------------------------------------------------
                
                setSize(570, 600);
                setTitle("CIMMYT Maize Scanner");
                setLayout(new BorderLayout(5, 5));
                add(tabbedPane, BorderLayout.CENTER);
                
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                                exit();
                        }
                });
        }
        
        public static final boolean isScannerRunning() {
                return scannerRunning;
        }
        
        public static final boolean isMacroRunning() {
                return macrosRunning;
        }
        
        public static final void setScannerRunning(boolean running) {
                scannerRunning = running;
        }
        
        public static final void setMacroRunning(boolean running) {
                macrosRunning = running;
        }
        
        private final void exit() {
                StringBuilder sb = new StringBuilder();
                
                if(scannerRunning || macrosRunning) {
                        sb.append("There are still tasks running such as");
                        if(scannerRunning) {
                                sb.append(" \"Scanner\"");
                        }
                        
                        if(macrosRunning) {
                                if(scannerRunning) {
                                        sb.append(" and");
                                }
                                
                                sb.append(" \"Canopy Macros\"");
                        }
                        
                        sb.append(". Exiting now may leave results unfinished, still exit ?");
                }
                else {
                        sb.append("Exit the plugin ?");
                }
                
                String msg = sb.toString();
                
                int choice = JOptionPane.showConfirmDialog(this, msg, "Exit Plugin ?", JOptionPane.YES_NO_CANCEL_OPTION);
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
