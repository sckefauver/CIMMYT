package cimmyt.maize.options;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 19, 2015
 *
 */
public class ClaheOptions implements ProcessOptions {
        
        private Options optionKey = null;
        
        private int blockSize = 0;
        private int histogramBins = 0;
        private float maximumSlope = 0.0f;
        private boolean fast = false;
        
        public ClaheOptions() {
                
        }

        public final int getBlockSize() {
                return blockSize;
        }

        public final void setBlockSize(int blockSize) {
                this.blockSize = blockSize;
        }

        public final int getHistogramBins() {
                return histogramBins;
        }

        public final void setHistogramBins(int histogramBins) {
                this.histogramBins = histogramBins;
        }

        public final float getMaximumSlope() {
                return maximumSlope;
        }

        public final void setMaximumSlope(float maximumSlope) {
                this.maximumSlope = maximumSlope;
        }

        public final boolean isFast() {
                return fast;
        }

        public final void setFast(boolean fast) {
                this.fast = fast;
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
