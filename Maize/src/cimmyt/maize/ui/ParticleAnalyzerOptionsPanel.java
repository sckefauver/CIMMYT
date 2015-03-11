package cimmyt.maize.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import layout.TableLayout;
import cimmyt.maize.ui.events.Events;
import cimmyt.maize.ui.tools.JCustomTextField;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 12, 2015
 *
 */
public class ParticleAnalyzerOptionsPanel extends JPanel implements ActionListener {

        private static final long serialVersionUID = -8003418614327479257L;
        
        private EventListenerList eventListenerList = new EventListenerList();
        private int eventId = 0;
        
        private JLabel sizeLabel = null;
        private JCustomTextField sizeFromField = null;
        private JLabel sizeToLabel = null;
        private JCustomTextField sizeToField = null;
        private JCheckBox infinityCheckBox = null;
        
        private JLabel circLabel = null;
        private JCustomTextField circFromField = null;
        private JLabel circToLabel = null;
        private JCustomTextField circToField = null;
        
        private JButton addButton = null;
        private JButton delButton = null;
        
        public ParticleAnalyzerOptionsPanel() {
                sizeLabel = new JLabel("Size:");
                sizeLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                sizeFromField = new JCustomTextField(5, 10);
                sizeFromField.setRegexFilter("\\d");
                
                sizeToLabel = new JLabel("to");
                sizeToLabel.setHorizontalAlignment(JLabel.CENTER);
                
                sizeToField = new JCustomTextField(5, 10);
                sizeToField.setRegexFilter("\\d");
                
                infinityCheckBox = new JCheckBox("Infinity", false);
                infinityCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                infinityCheckBox_actionPerformed(); 
                        }
                });
                
                // --------------------------------------------------------
                
                circLabel = new JLabel("Circularity:");
                circLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                circFromField = new JCustomTextField(5, 10);
                circFromField.setRegexFilter("\\d|\\.");
                
                circToLabel = new JLabel("to");
                circToLabel.setHorizontalAlignment(JLabel.CENTER);
                
                circToField = new JCustomTextField(5, 10);
                circToField.setRegexFilter("\\d|\\.");
                
                //TODO use this regex for final number validation "^\\d+(\\.\\d{1,2})?$"
                
                // --------------------------------------------------------
                
                addButton = new JButton("+");
                addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                addButton_actionPerformed();
                        }
                });
                
                delButton = new JButton("-");
                delButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                delButton_actionPerformed();
                        }
                });
                
                // --------------------------------------------------------
                
                /*
                 * Layout
                 */
                double spacer = 5;
                double[][] layoutSize = {
                                //                   0,      1,                2,      3,                     4,      5,                6,      7,                     8
                                {TableLayout.PREFERRED, spacer, TableLayout.FILL, spacer, TableLayout.PREFERRED, spacer, TableLayout.FILL, spacer, TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED} //2
                };
                
                setLayout(new TableLayout(layoutSize));
                add(sizeLabel,        "0, 0");
                add(sizeFromField,    "2, 0");
                add(sizeToLabel,      "4, 0");
                add(sizeToField,      "6, 0");
                add(infinityCheckBox, "8, 0");
                
                add(circLabel,        "0, 2");
                add(circFromField,    "2, 2");
                add(circToLabel,      "4, 2");
                add(circToField,      "6, 2");
        }
        
        private final void infinityCheckBox_actionPerformed() {
                if(infinityCheckBox.isSelected()) {
                        sizeToField.setRegexFilter("Infinity");
                        sizeToField.setText("Infinity");
                        sizeToField.setEditable(false);
                }
                else {
                        sizeToField.setRegexFilter("\\d{1,10}");
                        sizeToField.setText("");
                        sizeToField.setEditable(true);
                }
        }
        
        private final void addButton_actionPerformed() {
                actionPerformed(new ActionEvent(this, getEventID(), Events.ADD.getEventString()));
        }
        
        private final void delButton_actionPerformed() {
                actionPerformed(new ActionEvent(this, getEventID(), Events.DEL.getEventString()));
        }
        
        /**
         * <p>Returns a unique event id.</p>
         * 
         * @return int
         */
        private int getEventID()
        {
                int id = 0;
                if((eventId + 1) < Integer.MAX_VALUE) {
                        id++;
                }
                else {
                        id = 1;
                }

                return id;
        }
        
        /**
         * <p>Fires an action event to all listeners on this class.</p>
         * 
         * @param actionEvent
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
                Object[] listeners = eventListenerList.getListenerList();
                for(int i = listeners.length - 2; i >= 0; i -= 2) {
                        if(listeners[i] == ActionListener.class) {
                                ((ActionListener) listeners[i + 1]).actionPerformed(actionEvent);
                        }
                }
        }
        
        /**
         * <p>Adds an action listener to this class.</p>
         * 
         * @param actionListener
         */
        public void addActionListener(ActionListener actionListener) {
                eventListenerList.add(ActionListener.class, actionListener);
        }
        
        /**
         * <p>Removes an action listener from this class.</p>
         * 
         * @param actionListener
         */
        public void removeActionListener(ActionListener actionListener) {
                eventListenerList.remove(ActionListener.class, actionListener);
        }
        
        public static final void main(String ... args) {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                frame.add(new ParticleAnalyzerOptionsPanel());
                frame.pack();
                frame.setVisible(true);
        }
}
