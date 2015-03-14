package cimmyt.maize.ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import layout.TableLayout;
import cimmyt.maize.ui.tools.UITool;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 12, 2015
 *
 */
public class MaizeFrame extends JFrame {

        private static final long serialVersionUID = 6629966401349704225L;
        private JPanel mainPanel = null;
        private FileSelectPanel fileSelectPanel = null;
        private ParticleAnalyzerPanel particleAnalyzerPanel = null;
        private JScrollPane particleAnalyzerScrollPane = null;
        
        public MaizeFrame() {
                init();
        }

        private final void init() {
                fileSelectPanel = new FileSelectPanel();
                particleAnalyzerPanel = new ParticleAnalyzerPanel();
                particleAnalyzerScrollPane = new JScrollPane(particleAnalyzerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
                double spacer = 5;
                double[][] layoutSize = {
                                //                   0
                                {TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.FILL}      //2
                };
                
                mainPanel = new JPanel(new TableLayout(layoutSize));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                mainPanel.add(fileSelectPanel, "0, 0");
                mainPanel.add(particleAnalyzerScrollPane, "0, 2");
                
                setSize(550, 500);
                setTitle("CIMMYT Maize Scanner");
                setLayout(new BorderLayout(5, 5));
                add(mainPanel, BorderLayout.CENTER);
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
