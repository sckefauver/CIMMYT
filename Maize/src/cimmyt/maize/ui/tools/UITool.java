package cimmyt.maize.ui.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultEditorKit;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 12, 2015
 *
 */
public final class UITool {

        private UITool() {

        }

        /**
         * <p>Centers an given Window to the user's screen.</p>
         * 
         * @param w - the window to center
         */
        public static void center(Window w) {
                int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
                int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

                int windowWidth = w.getWidth();
                int windowHeight = w.getHeight();

                if (windowHeight > screenHeight) {
                        return;
                }

                if (windowWidth > screenWidth) {
                        return;
                }

                int x = (screenWidth - windowWidth) / 2;
                int y = (screenHeight - windowHeight) / 2;

                w.setLocation(x, y);
        }

        /**
         * <p>Sets the state of a frame to the maximized state.</p>
         * 
         * @param frame - the frame to maximize
         */
        public static void maximizeFrame(Frame frame) {
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        }

        /**
         * <p>Sets the state of a frame to the iconified state (ie, minimized)</p>
         * 
         * @param frame - the frame to iconify
         */
        public static void iconifyFrame(Frame frame) {
                frame.setExtendedState(Frame.ICONIFIED);
        }

        /**
         * <p>Restores the frame to its normal state.</p>
         * 
         * @param frame - the frame to restore
         */
        public static void restoreFrame(Frame frame) {
                frame.setExtendedState(Frame.NORMAL);
        }

        /**
         * <p>Returns the current height of the screen.</p>
         * 
         * @return int - the screen height
         */
        public static int getScreenHeight() {
                return Toolkit.getDefaultToolkit().getScreenSize().height;
        }

        /**
         * <p>Returns the current width of the screen.</p>
         * 
         * @return int - the screen width
         */
        public static int getScreenWidth() {
                return Toolkit.getDefaultToolkit().getScreenSize().width;
        }

        /**
         * <p>Adds 3 JMenuItems that perform Cut/Copy/Paste for any standard JMenu.</p>
         * 
         * @param menu - the menu to append the CCP menu items
         */
        public static void addCCPMenuItems(JMenu menu) {
                JMenuItem menuItem = null;

                menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
                menuItem.setText("Cut");
                menuItem.setMnemonic(KeyEvent.VK_T);
                menu.add(menuItem);

                menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
                menuItem.setText("Copy");
                menuItem.setMnemonic(KeyEvent.VK_C);
                menu.add(menuItem);

                menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
                menuItem.setText("Paste");
                menuItem.setMnemonic(KeyEvent.VK_P);
                menu.add(menuItem);
        }

        /**
         * <p>Adds 3 JMenuItems that perform Cut/Copy/Paste for any standard JPopupMenu.</p>
         * 
         * @param popupMenu - the popupMenu to append the CCP menu items
         */
        public static void addCCPMenuItem(JPopupMenu popupMenu) {
                JMenuItem menuItem = null;

                menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
                menuItem.setText("Cut");
                menuItem.setMnemonic(KeyEvent.VK_T);
                menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
                popupMenu.add(menuItem);

                menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
                menuItem.setText("Copy");
                menuItem.setMnemonic(KeyEvent.VK_C);
                menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
                popupMenu.add(menuItem);

                menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
                menuItem.setText("Paste");
                menuItem.setMnemonic(KeyEvent.VK_P);
                menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
                popupMenu.add(menuItem);
        }
        
        /**
         * 
         * @param path - the path and name of the image
         * @return the icon as an {@link ImageIcon} object
         */
        public static ImageIcon getImageIcon(String path) {
                ImageIcon imageIcon = null;
                URL iconURL = getIconPath(path); 
                
                if(iconURL != null) {
                        imageIcon = new ImageIcon(iconURL);
                }
                
                return imageIcon;
        }
        
        /**
         * <p>Returns the URL of an ImageIcon given its path and name.</p>
         * 
         * @param iconPath - the path and name of the icon
         * @return the {@link URL} of the icon
         */
        public static URL getIconPath(String iconPath) {
                URL iconURL = null;
                iconURL = UITool.class.getResource(iconPath);
                return iconURL;
        }
        
        /**
         * <p>Displays a splash window for <code>duration</code> milliseconds displaying the specified <code>image</code>.</p>
         * 
         * @param image
         *      - the image icon to display
         * @param duration
         *      - how long to show the window (milliseconds)
         * @param windowSize
         *      - the size of the window
         * @param frameOwner
         *      - the parent window or owner, null if none
         */
        public static void showSplashWindow(ImageIcon image, int duration, Dimension windowSize, Window frameOwner) {
                showSplashWindow(image, duration, windowSize, frameOwner, null);
        }
        
        /**
         * <p>Displays a splash window for <code>duration</code> milliseconds displaying the specified <code>image</code>.
         * An optional <code>actionListener</code> can be attached to perform a function after the splash window has
         * closed. If null is specified then the default listener will be used.</p>
         * 
         * @param image
         *      - the image icon to display
         * @param duration
         *      - how long to show the window (milliseconds)
         * @param windowSize
         *      - the size of the window
         * @param frameOwner
         *      - the parent window or owner, null if none
         * @param actionListener
         *      - a custom actionListener to be performed after the splash window is closed
         */
        public static void showSplashWindow(ImageIcon image, int duration, Dimension windowSize, Window frameOwner, ActionListener actionListener) {
                JLabel splashImage = new JLabel();
                splashImage.setHorizontalAlignment(JLabel.CENTER);
                splashImage.setOpaque(true);
                splashImage.setIcon(image);

                final JWindow window = new JWindow(frameOwner);
                window.add(splashImage, BorderLayout.CENTER);
                window.setSize(windowSize);
                splashImage.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                UITool.center(window);
                window.setVisible(true);
                
                ActionListener defaultActionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                window.setVisible(false);
                                window.dispose();
                        }
                };
                
                Timer timer = new Timer(duration, defaultActionListener);
                if(actionListener != null) {
                        timer.addActionListener(actionListener);
                }

                timer.setRepeats(false);
                timer.start();
        }
}
