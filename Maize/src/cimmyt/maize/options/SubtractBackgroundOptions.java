package cimmyt.maize.options;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Mar 19, 2015
 *
 */
public class SubtractBackgroundOptions implements ProcessOptions {

        private ProcessOption optionKey = null;
        
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
        public void setOptionKey(ProcessOption optionKey) {
                this.optionKey = optionKey;
        }

        @Override
        public ProcessOption getOptionKey() {
                return optionKey;
        }
}
