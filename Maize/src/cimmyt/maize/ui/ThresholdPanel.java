package cimmyt.maize.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import cimmyt.maize.options.Options;
import cimmyt.maize.options.ThresholdOptions;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 20, 2015
 *
 */
public class ThresholdPanel extends JPanel {
        
        private static final long serialVersionUID = 407485483430514053L;
        
        private static final String[] THRESHOLD_METHODS = new String[] { "Default", "Huang", "Intermodes", "IsoData", "IJ_IsoData", "Li", "MaxEntropy", "Mean", "MinError", "Minimum", "Moments", "Otsu", "Percentile", "RenyiEntropy", "Shanbhag", "Triangle", "Yen"};
        
        private JLabel thresholdLabel = null;
        private JComboBox<String> thresholdComboBox = null;
        private JCheckBox thresholdDarkBbgCheckBox = null;
        private ThresholdOptions thresholdOptions = new ThresholdOptions();
        
        public ThresholdPanel() {
                thresholdOptions.setOptionKey(Options.THRESHOLD);
                
                thresholdLabel = new JLabel("Auto Threshold Method:");
                thresholdLabel.setHorizontalAlignment(JLabel.RIGHT);
                thresholdLabel.setPreferredSize(new Dimension(150, 25));
                
                thresholdComboBox = new JComboBox<String>(THRESHOLD_METHODS);
                thresholdComboBox.setSelectedItem("Default");
                thresholdDarkBbgCheckBox = new JCheckBox("Dark Background", false);
                
                setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                add(thresholdLabel);
                add(thresholdComboBox);
                add(thresholdDarkBbgCheckBox);
        }
        
        @Override
        public void setEnabled(boolean enabled) {
                thresholdComboBox.setEnabled(enabled);
                thresholdDarkBbgCheckBox.setEnabled(enabled);
                super.setEnabled(enabled);
        }
        
        public ThresholdOptions getOptions() {
                thresholdOptions.setThresholdMethod(thresholdComboBox.getSelectedItem().toString());
                thresholdOptions.setDarkBackground(thresholdDarkBbgCheckBox.isSelected());
                return thresholdOptions;
        }
}
