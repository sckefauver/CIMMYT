package cimmyt.maize.ui.analysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import layout.TableLayout;
import cimmyt.maize.options.AnalysisOption;
import cimmyt.maize.options.ParticleAnalysisOptions;
import cimmyt.maize.ui.events.Events;
import cimmyt.maize.ui.tools.JCustomTextField;
import cimmyt.maize.ui.tools.UITool;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 12, 2015
 *
 */
public class ParticleAnalyzerOptionsPanel extends JPanel implements ActionListener {

        private static final long serialVersionUID = -8003418614327479257L;
        private static final Pattern DECIMAL_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
        
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
        
        private JPanel optionsPanel = null;
        private JPanel buttonPanel = null;
        
        private ParticleAnalysisOptions analysisOptions = new ParticleAnalysisOptions();
        
        public ParticleAnalyzerOptionsPanel() {
                analysisOptions.setOptionKey(AnalysisOption.PARTICLE_ANALYSIS);
                
                sizeLabel = new JLabel("Size:");
                sizeLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                sizeFromField = new JCustomTextField(8, 10);
                sizeFromField.setRegexFilter("\\d");
                sizeFromField.setText("0");
                
                sizeToLabel = new JLabel("to");
                sizeToLabel.setHorizontalAlignment(JLabel.CENTER);
                
                //TODO max size cannot go over 999999 as per FIJI restrictions
                //Fiji will detect 999999 and assign positive infinity instead
                sizeToField = new JCustomTextField(8, 10);
                sizeToField.setRegexFilter("\\d");
                
                infinityCheckBox = new JCheckBox("Infinity", true);
                infinityCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                infinityCheckBox_actionPerformed();
                        }
                });
                
                infinityCheckBox_actionPerformed();
                
                // --------------------------------------------------------
                
                circLabel = new JLabel("Circularity:");
                circLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                circFromField = new JCustomTextField(5, 10);
                circFromField.setText("0.0");
                circFromField.setRegexFilter("\\d|\\.");
                circFromField.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                                validateDecimal_focusLost(e);
                        }
                });
                
                circToLabel = new JLabel("to");
                circToLabel.setHorizontalAlignment(JLabel.CENTER);
                
                circToField = new JCustomTextField(5, 10);
                circToField.setText("1.0");
                circToField.setRegexFilter("\\d|\\.");
                circToField.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                                validateDecimal_focusLost(e);
                        }
                });
                
                // --------------------------------------------------------
                
                addButton = new JButton(UITool.getImageIcon("/cimmyt/maize/ui/icons/add_22.png"));
                addButton.setPreferredSize(new Dimension(48, 48));
                addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                addButton_actionPerformed();
                        }
                });
                
                delButton = new JButton(UITool.getImageIcon("/cimmyt/maize/ui/icons/remove_22.png"));
                delButton.setPreferredSize(new Dimension(48, 48));
                delButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                delButton_actionPerformed();
                        }
                });
                
                // --------------------------------------------------------
                
                double spacer = 5;
                double[][] layoutSize = {
                                //                   0,      1,                2,      3,                     4,      5,                6,      7,                     8
                                {TableLayout.PREFERRED, spacer, TableLayout.FILL, spacer, TableLayout.PREFERRED, spacer, TableLayout.FILL, spacer, TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED} //2
                };
                
                optionsPanel = new JPanel(new TableLayout(layoutSize));
                optionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                optionsPanel.add(sizeLabel,        "0, 0");
                optionsPanel.add(sizeFromField,    "2, 0");
                optionsPanel.add(sizeToLabel,      "4, 0");
                optionsPanel.add(sizeToField,      "6, 0");
                optionsPanel.add(infinityCheckBox, "8, 0");
                optionsPanel.add(circLabel,        "0, 2");
                optionsPanel.add(circFromField,    "2, 2");
                optionsPanel.add(circToLabel,      "4, 2");
                optionsPanel.add(circToField,      "6, 2");
                
                // --------------------------------------------------------
                
                buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
                buttonPanel.add(addButton);
                buttonPanel.add(delButton);
                
                // --------------------------------------------------------
                
                setLayout(new BorderLayout(5, 5));
                setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
                add(optionsPanel, BorderLayout.CENTER);
                add(buttonPanel, BorderLayout.EAST);
        }
        
        public final void setAddButtonEnabled(boolean enable) {
                addButton.setEnabled(enable);
        }
        
        public final void setDelButtonEnable(boolean enable) {
                delButton.setEnabled(enable);
        }
        
        private final void infinityCheckBox_actionPerformed() {
                if(infinityCheckBox.isSelected()) {
                        sizeToField.setRegexFilter("Infinity");
                        sizeToField.setText("Infinity");
                        sizeToField.setEditable(false);
                        sizeToField.setBackground(Color.WHITE);
                }
                else {
                        sizeToField.setRegexFilter("\\d{1,10}");
                        sizeToField.setText("");
                        sizeToField.setEditable(true);
                }
        }
        
        private final void validateDecimal_focusLost(FocusEvent e) {
                JCustomTextField source = (JCustomTextField)e.getSource();
                String text = source.getText();
                
                if(!DECIMAL_PATTERN.matcher(text).matches()) {
                        JOptionPane.showMessageDialog(this, "Circularity value must be a decimal to 2 decimal places");
                }
        }
        
        private final void addButton_actionPerformed() {
                actionPerformed(new ActionEvent(this, getEventID(), Events.ADD.getEventString()));
        }
        
        private final void delButton_actionPerformed() {
                actionPerformed(new ActionEvent(this, getEventID(), Events.DEL.getEventString()));
        }
        
        public final ParticleAnalysisOptions getAnalysisOptions() {
                String minSize = sizeFromField.getText();
                analysisOptions.setMinParticleSize(Double.parseDouble(minSize));
                
                if(infinityCheckBox.isSelected()) {
                        analysisOptions.setMaxParticleSizeInfinity(true);
                }
                else {
                        String maxSize = sizeToField.getText();
                        analysisOptions.setMaxParticleSize(Double.parseDouble(maxSize));
                        analysisOptions.setMaxParticleSizeInfinity(false);
                }
                
                String minCirc = circFromField.getText();
                String maxCirc = circToField.getText();
                
                analysisOptions.setMinParticleCirc(Double.parseDouble(minCirc));
                analysisOptions.setMaxParticleCirc(Double.parseDouble(maxCirc));
                
                return analysisOptions;
        }
        
        private boolean addButtonSavedState = false;
        private boolean delButtonSavedState = false;
        
        @Override
        public void setEnabled(boolean enabled) {
                sizeFromField.setEnabled(enabled);
                sizeToField.setEnabled(enabled);
                infinityCheckBox.setEnabled(enabled);
                circFromField.setEnabled(enabled);
                circToField.setEnabled(enabled);
                
                if(!enabled) {
                        addButtonSavedState = addButton.isEnabled();
                        delButtonSavedState = delButton.isEnabled();
                        addButton.setEnabled(enabled);
                        delButton.setEnabled(enabled);
                }
                else {
                        addButton.setEnabled(addButtonSavedState);
                        delButton.setEnabled(delButtonSavedState);
                }
                
                super.setEnabled(enabled);
        }
        
        /**
         * <p>Returns a unique event id.</p>
         * 
         * @return a unique event id
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
         * @param actionEvent - the {@link ActionEvent} object to associate with this event
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
         * @param actionListener - the action listener to add
         */
        public void addActionListener(ActionListener actionListener) {
                eventListenerList.add(ActionListener.class, actionListener);
        }
        
        /**
         * <p>Removes an action listener from this class.</p>
         * 
         * @param actionListener - the action listener to remove
         */
        public void removeActionListener(ActionListener actionListener) {
                eventListenerList.remove(ActionListener.class, actionListener);
        }
}
