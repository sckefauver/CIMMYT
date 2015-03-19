package cimmyt.maize.options;


/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 20, 2015
 *
 */
public class RemoveOutlierOptions implements ProcessOptions {

        public static enum Outliers {
                BRIGHT(0),
                DARK(1);
                
                int val = 0;
                Outliers(int val) {
                        this.val = val;
                }
                
                public final int intValue() {
                        return val;
                }
        }
        
        private Options optionKey = null;
        
        private double radius = 0.0;
        private float threshold = 0;
        private Outliers whichOutlier = null;
        
        public RemoveOutlierOptions() {
                
        }

        public final double getRadius() {
                return radius;
        }

        public final void setRadius(double radius) {
                this.radius = radius;
        }

        public final float getThreshold() {
                return threshold;
        }

        public final void setThreshold(float threshold) {
                this.threshold = threshold;
        }

        public final Outliers getWhichOutlier() {
                return whichOutlier;
        }

        public final void setWhichOutlier(Outliers whichOutlier) {
                this.whichOutlier = whichOutlier;
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
