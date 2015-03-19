package cimmyt.maize.options;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 19, 2015
 *
 */
public class SubtractBackgroundOptions implements ProcessOptions {

        private Options optionKey = null;
        
        private double rollingBallRadius = 0.0;
        private boolean lightBackground = false;
        
        public SubtractBackgroundOptions() {
                
        }

        public final double getRollingBallRadius() {
                return rollingBallRadius;
        }

        public final void setRollingBallRadius(double rollingBallRadius) {
                this.rollingBallRadius = rollingBallRadius;
        }

        public final boolean isLightBackground() {
                return lightBackground;
        }

        public final void setLightBackground(boolean lightBackground) {
                this.lightBackground = lightBackground;
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
