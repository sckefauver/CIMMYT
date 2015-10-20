package cimmyt.maize.plugins;

import ij.ImagePlus;
import ij.Prefs;
import ij.process.ImageProcessor;
import java.awt.Rectangle;


/**
 * <p>Modified by George El-Haddad (george.dma@gmail.com)</p>
 * 
 * @version 1.0;  23 Dec 2004
 * @author Dimiter Prodanov
 * @author University of Leiden
 * @contents This plugin separately measures the red, green and blue channels of an RGB image
 *  between user-definable threshold levels per channel. It is best used together with the
 *  Threshold_Colour plugin (by Bob Dougherty  and Gabriel Landini) or my ColorHistogram.
 *  The idea and original realization of this plugin belong to	Wayne Rasband, NIH
 *
 * @license This library is free software; you can redistribute it and/or
 *      modify it under the terms of the GNU Lesser General Public
 *      License as published by the Free Software Foundation; either
 *      version 2.1 of the License, or (at your option) any later version.
 *
 *      This library is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *       Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public
 *      License along with this library; if not, write to the Free Software
 *      Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
public class RGB_Measure_Plus {

        private static final String RTMIN = "rTmin";
        private static final String RTMAX = "rTmax";
        private static final String GTMIN = "gTmin";
        private static final String GTMAX = "gTmax";
        private static final String BTMIN = "bTmin";
        private static final String BTMAX = "bTmax";

        private static int rtmin = Prefs.getInt(RTMIN, 0);
        private static int rtmax = Prefs.getInt(RTMAX, 255);
        private static int gtmin = Prefs.getInt(GTMIN, 0);
        private static int gtmax = Prefs.getInt(GTMAX, 255);
        private static int btmin = Prefs.getInt(BTMIN, 0);
        private static int btmax = Prefs.getInt(BTMAX, 255);

        private int[] tamin = new int[3];
        private int[] tamax = new int[3];
        private byte[] mask;
        private int area = 1;
//        private int width;
//        private int height = 1;
        
        /**
         * stores the histogram means
         */
        private double[] histMean = new double[3];
        
        /**
         * stores the histogram area per channel
         */
        private double[] histArea = new double[3];
        
        /**
         * stores the histogram standard deviation
         */
        private double[] histStdev = new double[3];
        
        /**
         * stores the 3-channel ROI histogram
         */
        private int[][] histogram = null;
        
        /**
         * Value of pixels included in masks
         */
        private static final int BLACK = 0xFF000000;
        
        public RGB_Measure_Plus() {
                
        }
        
        public static final int getRtmin() {
                return rtmin;
        }

        public static final void setRtmin(int rtmin) {
                RGB_Measure_Plus.rtmin = rtmin;
        }

        public static final int getRtmax() {
                return rtmax;
        }

        public static final void setRtmax(int rtmax) {
                RGB_Measure_Plus.rtmax = rtmax;
        }

        public static final int getGtmin() {
                return gtmin;
        }

        public static final void setGtmin(int gtmin) {
                RGB_Measure_Plus.gtmin = gtmin;
        }

        public static final int getGtmax() {
                return gtmax;
        }

        public static final void setGtmax(int gtmax) {
                RGB_Measure_Plus.gtmax = gtmax;
        }

        public static final int getBtmin() {
                return btmin;
        }

        public static final void setBtmin(int btmin) {
                RGB_Measure_Plus.btmin = btmin;
        }

        public static final int getBtmax() {
                return btmax;
        }

        public static final void setBtmax(int btmax) {
                RGB_Measure_Plus.btmax = btmax;
        }
        
        private final void reset() {
                histMean[0] = Double.NaN;
                histMean[1] = Double.NaN;
                histMean[2] = Double.NaN;
                histArea[0] = Double.NaN;
                histArea[1] = Double.NaN;
                histArea[2] = Double.NaN;
                histStdev[0] = Double.NaN;
                histStdev[1] = Double.NaN;
                histStdev[2] = Double.NaN;
                histogram = null;
        }
        
        /**
         * runs the plugin
         */
        public final RgbMeasure measureRGB(ImagePlus imp) {
                reset();
                tamin[0] = rtmin;
                tamin[1] = gtmin;
                tamin[2] = btmin;
                tamax[0] = rtmax;
                tamax[1] = gtmax;
                tamax[2] = btmax;
                
                ImageProcessor ipmask = imp.getMask();
                
                if(ipmask != null) {
                        mask = (byte[])ipmask.getPixels();
                }
                else {
                        mask = null;
                }
                
                int width = imp.getWidth();
                int height = imp.getHeight();
                
                Rectangle rect;
                try {
                        rect = imp.getRoi().getBounds();
                        area = rect.width * rect.height;
                }
                catch (NullPointerException e) {
                        rect = new Rectangle(width, height);
                        area = width * height;
                }
                
                RgbMeasure rgbMeasure = null;
                Object obj = imp.getProcessor().getPixels();
                int[] pixels = conversionCheck(obj);
                
                if(pixels != null) {
                        if (mask != null) {
                                histogram = getHistogram(width, pixels, mask, rect);
                        }
                        else {
                                histogram = getHistogram(width, pixels, rect);
                        }
                        
                        calculateStatistics(histogram, tamin, tamax);
                        
                        rgbMeasure = new RgbMeasure();
                        rgbMeasure.setrMean(histMean[0]);
                        rgbMeasure.setgMean(histMean[1]);
                        rgbMeasure.setbMean(histMean[2]);
                        rgbMeasure.setrStdDev(histStdev[0]);
                        rgbMeasure.setgStdDev(histStdev[1]);
                        rgbMeasure.setbStdDev(histStdev[2]);
                        rgbMeasure.setrArea(histArea[0]);
                        rgbMeasure.setgArea(histArea[1]);
                        rgbMeasure.setbArea(histArea[2]);
                }
                else {
                        //All values initialized to NaN
                        rgbMeasure = new RgbMeasure();
                }

                return rgbMeasure;
        }
        
        private final int[] conversionCheck(Object obj) {
                int[] pixels = null;
                if(obj instanceof int[]) {
                        pixels = (int[])obj;
                }
                else if(obj instanceof short[]) {
                        short[] tmp = (short[])obj;
                        
                        pixels = new int[tmp.length];
                        for (int i = 0; i < pixels.length; i++) {
                                pixels[i] = tmp[i];
                        }
                }
                else if(obj instanceof byte[]) {
                        byte[] tmp = (byte[])obj;
                        
                        pixels = new int[tmp.length];
                        for (int i = 0; i < pixels.length; i++) {
                                pixels[i] = tmp[i];
                        }
                }
                else if(obj instanceof float[]) {
                        float[] tmp = (float[])obj;
                        
                        pixels = new int[tmp.length];
                        for (int i = 0; i < pixels.length; i++) {
                                pixels[i] = (int)tmp[i];
                        }
                }
                
                return pixels;
        }
        
        /**
         * calculates the histogram
         */
        private final int[][] getHistogram(int width, int[] pixels, Rectangle roi) {
                int c;
                int r;
                int g;
                int b;
                
                int roiY = roi.y;
                int roiX = roi.y;
                int roiWidth = roi.width;
                int roiHeight = roi.height;
                int[][] histogram = new int[3][256];
                
                for (int y = roiY; y < (roiY + roiHeight); y++) {
                        int i = y * width + roiX;
                        
                        for (int x = roiX; x < (roiX + roiWidth); x++) {
                                c = pixels[i++];
                                r = (c & 0xff0000) >> 16;
                                g = (c & 0xff00) >> 8;
                                b = c & 0xff;

                                histogram[0][r]++;
                                histogram[1][g]++;
                                histogram[2][b]++;
                        }
                }
                return histogram;
        }

        /**
         * calculates the histogram
         */
        private final int[][] getHistogram(int width, int[] pixels, byte[] mask, Rectangle roi) {
                int c;
                int r;
                int g;
                int b;

                int[][] histogram = new int[3][256];
                int roiY = roi.y;
                int roiX = roi.y;
                int roiWidth = roi.width;
                int roiHeight = roi.height;
                
                for (int y = roiY, my = 0; y < (roiY + roiHeight); y++, my++) {
                        int i = y * width + roiX;
                        int mi = my * roiWidth;
                        
                        for (int x = roiX; x < (roiX + roiWidth); x++) {
                                if (mask[mi++] != BLACK) {
                                        c = pixels[i];
                                        r = (c & 0xff0000) >> 16;
                                        g = (c & 0xff00) >> 8;
                                        b = c & 0xff;
                                        histogram[0][r]++;
                                        histogram[1][g]++;
                                        histogram[2][b]++;
                                }
                                
                                i++;
                        }
                }

                return histogram;
        }

        /**
         * calculates the statistics histogram between Tmin and Tmax
         */
        private final void calculateStatistics(int[][] histogram, int[] tmin, int[] tmax) {
                for (int col = 0; col < 3; col++) {
                        double cumsum = 0;
                        double cumsum2 = 0;
                        double aux = 0;
                        
                        for (int i = tmin[col]; i <= tmax[col]; i++) {
                                cumsum += i * histogram[col][i];
                                aux += histogram[col][i];
                                cumsum2 += i * i * histogram[col][i];
                        }

                        histMean[col] = cumsum / area;
                        histArea[col] = aux / area;
                        double stdDev = (area * cumsum2 - cumsum * cumsum) / area;
                        histStdev[col] = Math.sqrt(stdDev / (area - 1.0));
                }
        }
}
