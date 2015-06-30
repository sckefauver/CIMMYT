package cimmyt.maize.options;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 20, 2015
 *
 */
public class ThresholdOptions implements ProcessOptions {

        private ProcessOption optionKey = null;
        
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
        public void setOptionKey(ProcessOption optionKey) {
                this.optionKey = optionKey;
        }

        @Override
        public ProcessOption getOptionKey() {
                return optionKey;
        }
}
