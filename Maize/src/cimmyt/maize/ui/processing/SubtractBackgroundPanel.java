package cimmyt.maize.ui.processing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import cimmyt.maize.options.ProcessOption;
import cimmyt.maize.options.SubtractBackgroundOptions;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Mar 19, 2015
 *
 */
public class SubtractBackgroundPanel extends JPanel {
        
        private static final long serialVersionUID = 4889069012123614406L;
        
        private JLabel subBackgroundLabel = null;
        private JSpinner subBackgroundSpinner = null;
        private JCheckBox lightCheckBox = null;
        private SubtractBackgroundOptions subtractBackgroundOptions = new SubtractBackgroundOptions();
        
        public SubtractBackgroundPanel() {
                subtractBackgroundOptions.setOptionKey(ProcessOption.SUBTRACT_BACKGROUND);
                
                subBackgroundLabel = new JLabel("Subtract Background:");
                subBackgroundLabel.setHorizontalAlignment(JLabel.RIGHT);
                subBackgroundLabel.setPreferredSize(new Dimension(150, 25));
                
                subBackgroundSpinner = new JSpinner(new SpinnerNumberModel(50.0, 0.0, 90.0, 0.01));
                subBackgroundSpinner.setPreferredSize(new Dimension(60, 25));
                
                lightCheckBox = new JCheckBox("Light Background", true);
                
                setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                add(subBackgroundLabel);
                add(subBackgroundSpinner);
                add(lightCheckBox);
        }
        
        @Override
        public void setEnabled(boolean enabled) {
                subBackgroundSpinner.setEnabled(enabled);
                lightCheckBox.setEnabled(enabled);
                super.setEnabled(enabled);
        }
        
        public final SubtractBackgroundOptions getOptions() {
                subtractBackgroundOptions.setRollingBallRadius(((Double) subBackgroundSpinner.getValue()).floatValue());
                subtractBackgroundOptions.setLightBackground(lightCheckBox.isSelected());
                return subtractBackgroundOptions;
        }
}
