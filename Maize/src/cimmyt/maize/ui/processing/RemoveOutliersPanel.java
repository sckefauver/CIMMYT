package cimmyt.maize.ui.processing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import cimmyt.maize.options.ProcessOption;
import cimmyt.maize.options.RemoveOutlierOptions;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Mar 20, 2015
 *
 */
public class RemoveOutliersPanel extends JPanel {
        
        private static final long serialVersionUID = 8247900197269021486L;

        private static final String[] WHICH_OUTLIERS = new String[] { "Bright", "Dark"};
        
        private JLabel removeOutliersLabel = null;
        private JSpinner radiusSpinner = null;
        private JSpinner thresholdSpinner = null;
        private JComboBox<String> whichOutliersComboBox = null;
        private RemoveOutlierOptions removeOutliersOptions = new RemoveOutlierOptions();
        
        public RemoveOutliersPanel() {
                removeOutliersOptions.setOptionKey(ProcessOption.REMOVE_OUTLIERS);
                
                removeOutliersLabel = new JLabel("Remove Outliers (pixels):");
                removeOutliersLabel.setHorizontalAlignment(JLabel.RIGHT);
                removeOutliersLabel.setPreferredSize(new Dimension(150, 25));
                
                whichOutliersComboBox = new JComboBox<String>(WHICH_OUTLIERS);
                whichOutliersComboBox.setSelectedItem("Bright");
                
                radiusSpinner = new JSpinner(new SpinnerNumberModel(2.0, 0.0, 100000.0, 1.0));
                radiusSpinner.setPreferredSize(new Dimension(80, 25));
                
                thresholdSpinner = new JSpinner(new SpinnerNumberModel(50.0, 0.0, 100.0, 1.0));
                thresholdSpinner.setPreferredSize(new Dimension(80, 25));
                
                setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                add(removeOutliersLabel);
                add(radiusSpinner);
                add(thresholdSpinner);
                add(whichOutliersComboBox);
        }
        
        @Override
        public void setEnabled(boolean enabled) {
                radiusSpinner.setEnabled(enabled);
                thresholdSpinner.setEnabled(enabled);
                whichOutliersComboBox.setEnabled(enabled);
                super.setEnabled(enabled);
        }
        
        public RemoveOutlierOptions getOptions() {
                removeOutliersOptions.setRadius(((Double) radiusSpinner.getValue()).doubleValue());
                removeOutliersOptions.setThreshold(((Double) thresholdSpinner.getValue()).floatValue());
                
                String str = whichOutliersComboBox.getSelectedItem().toString();
                if("Bright".equals(str)) {
                        removeOutliersOptions.setWhichOutlier(RemoveOutlierOptions.Outliers.BRIGHT);
                }
                else {
                        removeOutliersOptions.setWhichOutlier(RemoveOutlierOptions.Outliers.DARK);
                }
                
                return removeOutliersOptions;
        }
}
