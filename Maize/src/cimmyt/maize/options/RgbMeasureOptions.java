package cimmyt.maize.options;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 30, 2015
 *
 */
public class RgbMeasureOptions implements AnalysisOptions {

        private AnalysisOption optionKey = null;
        private boolean measureRgb = false;

        public RgbMeasureOptions() {

        }

        public final boolean isMeasureRgb() {
                return measureRgb;
        }

        public final void setMeasureRgb(boolean measureRgb) {
                this.measureRgb = measureRgb;
        }
        
        @Override
        public void setOptionKey(AnalysisOption optionKey) {
                this.optionKey = optionKey;
        }

        @Override
        public AnalysisOption getOptionKey() {
                return optionKey;
        }
}
