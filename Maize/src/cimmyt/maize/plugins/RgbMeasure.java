package cimmyt.maize.plugins;


/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 30, 2015
 *
 */
public final class RgbMeasure {
        
        /*
         * RGB Mean
         */
        private double rMean = Double.NaN;
        private double gMean = Double.NaN;
        private double bMean = Double.NaN;
        
        /*
         * RGB Standard Deviation
         */
        private double rStdDev = Double.NaN;
        private double gStdDev = Double.NaN;
        private double bStdDev = Double.NaN;
        
        /*
         * RGB Area Fraction
         */
        private double rArea = Double.NaN;
        private double gArea = Double.NaN;
        private double bArea = Double.NaN;
        
        public RgbMeasure() {
                
        }

        public final double getrMean() {
                return rMean;
        }

        public final void setrMean(double rMean) {
                this.rMean = rMean;
        }

        public final double getgMean() {
                return gMean;
        }

        public final void setgMean(double gMean) {
                this.gMean = gMean;
        }

        public final double getbMean() {
                return bMean;
        }

        public final void setbMean(double bMean) {
                this.bMean = bMean;
        }

        public final double getrStdDev() {
                return rStdDev;
        }

        public final void setrStdDev(double rStdDev) {
                this.rStdDev = rStdDev;
        }

        public final double getgStdDev() {
                return gStdDev;
        }

        public final void setgStdDev(double gStdDev) {
                this.gStdDev = gStdDev;
        }

        public final double getbStdDev() {
                return bStdDev;
        }

        public final void setbStdDev(double bStdDev) {
                this.bStdDev = bStdDev;
        }

        public final double getrArea() {
                return rArea;
        }

        public final void setrArea(double rArea) {
                this.rArea = rArea;
        }

        public final double getgArea() {
                return gArea;
        }

        public final void setgArea(double gArea) {
                this.gArea = gArea;
        }

        public final double getbArea() {
                return bArea;
        }

        public final void setbArea(double bArea) {
                this.bArea = bArea;
        }
}
