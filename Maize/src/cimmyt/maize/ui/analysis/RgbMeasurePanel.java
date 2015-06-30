package cimmyt.maize.ui.analysis;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import cimmyt.maize.options.RgbMeasureOptions;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 30, 2015
 *
 */
public class RgbMeasurePanel extends JPanel {
        
        private static final long serialVersionUID = -3724417906340850981L;
        
        private JCheckBox rgbMeasureCheckBox = null;
        private RgbMeasureOptions rgbMeasureOptions = new RgbMeasureOptions();
        
        public RgbMeasurePanel() {
                rgbMeasureCheckBox = new JCheckBox("Measure RGB", false);
                
                setLayout(new BorderLayout(5, 5));
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                add(rgbMeasureCheckBox, BorderLayout.CENTER);
                
                setEnabled(false);
        }
        
        @Override
        public void setEnabled(boolean enabled) {
                rgbMeasureCheckBox.setEnabled(enabled);
                super.setEnabled(enabled);
        }
        
        public final RgbMeasureOptions getAnalysisOptions() {
                rgbMeasureOptions.setMeasureRgb(rgbMeasureCheckBox.isSelected());
                return rgbMeasureOptions;
        }
}
