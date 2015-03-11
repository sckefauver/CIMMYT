package cimmyt.maize.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import cimmyt.maize.ui.tools.UITool;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 12, 2015
 *
 */
public class MaizeFrame extends JFrame {

        private static final long serialVersionUID = 6629966401349704225L;
        private FileSelectPanel fileSelectPanel = null;
        
        public MaizeFrame() {
                init();
        }

        private final void init() {
                fileSelectPanel = new FileSelectPanel();
                
                setTitle("CIMMYT Maize Scanner");
                setLayout(new BorderLayout(5, 5));
                add(fileSelectPanel, BorderLayout.NORTH);
                //add(buttonPanel, BorderLayout.SOUTH);
                pack();
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                                exit();
                        }
                });
        }
        
        private final void exit() {
                int choice = JOptionPane.showConfirmDialog(this, "Do you wish to exit the plugin ?", "Exit Plugin ?", JOptionPane.YES_NO_CANCEL_OPTION);
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
