package cimmyt.maize.options;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 20, 2015
 *
 */
public class ThresholdOptions implements ProcessOptions {

        private Options optionKey = null;
        
        private String thresholdMethod = null;
        private boolean darkBackground = false;
        
        public ThresholdOptions() {
                
        }

        public final String getThresholdMethod() {
                return thresholdMethod;
        }

        public final void setThresholdMethod(String thresholdMethod) {
                this.thresholdMethod = thresholdMethod;
        }

        public final boolean isDarkBackground() {
                return darkBackground;
        }

        public final void setDarkBackground(boolean darkBackground) {
                this.darkBackground = darkBackground;
        }
        
        @Override
        public void setOptionKey(Options optionKey) {
                this.optionKey = optionKey;
        }

        @Override
        public Options getOptionKey() {
                return optionKey;
        }
}
