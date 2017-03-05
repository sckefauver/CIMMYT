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
import cimmyt.maize.options.ClaheOptions;
import cimmyt.maize.options.ProcessOption;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Mar 19, 2015
 *
 */
public class ClahePanel extends JPanel {
        
        private static final long serialVersionUID = 2343074941971137259L;
        
        private JLabel claheLabel = null;
        private JSpinner blockSizeSpinner = null;
        private JSpinner binsSpinner = null;
        private JSpinner slopeSpinner = null;
        private JCheckBox fastCheckBox = null;
        private ClaheOptions claheOptions = new ClaheOptions();
        
        public ClahePanel() {
                claheOptions.setOptionKey(ProcessOption.ENHANCE_LOCAL_CONTRAST);
                
                claheLabel = new JLabel("Enhance Local Contrast: ");
                claheLabel.setHorizontalAlignment(JLabel.RIGHT);
                claheLabel.setPreferredSize(new Dimension(150, 25));
                
                blockSizeSpinner = new JSpinner(new SpinnerNumberModel(127, 0, 255, 1));
                blockSizeSpinner.setPreferredSize(new Dimension(60, 25));
                blockSizeSpinner.setToolTipText("Block Size");
                
                binsSpinner = new JSpinner(new SpinnerNumberModel(256, 0, 999, 1));
                binsSpinner.setPreferredSize(new Dimension(60, 25));
                binsSpinner.setToolTipText("Histogram Bins");

                slopeSpinner = new JSpinner(new SpinnerNumberModel(3.0, 0.0, 100.0, 0.01));
                slopeSpinner.setPreferredSize(new Dimension(60, 25));
                slopeSpinner.setToolTipText("Maximum Slope");
                
                fastCheckBox = new JCheckBox("Fast (less accurate)", true);
                
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                add(claheLabel);
                add(blockSizeSpinner);
                add(binsSpinner);
                add(slopeSpinner);
                add(fastCheckBox);
        }
        
        @Override
        public void setEnabled(boolean enabled) {
                blockSizeSpinner.setEnabled(enabled);
                binsSpinner.setEnabled(enabled);
                slopeSpinner.setEnabled(enabled);
                fastCheckBox.setEnabled(enabled);
                super.setEnabled(enabled);
        }
        
        public final ClaheOptions getOptions() {
                claheOptions.setFast(fastCheckBox.isSelected());
                claheOptions.setBlockSize(((Integer) blockSizeSpinner.getValue()).intValue());
                claheOptions.setHistogramBins(((Integer) binsSpinner.getValue()).intValue());
                claheOptions.setMaximumSlope(((Double) slopeSpinner.getValue()).floatValue());
                return claheOptions;
        }
}
