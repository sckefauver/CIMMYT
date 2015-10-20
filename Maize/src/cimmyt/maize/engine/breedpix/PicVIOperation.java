package cimmyt.maize.engine.breedpix;

import java.awt.Rectangle;
import java.awt.color.ColorSpace;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;

/**
 * Copyright 2012 Jaume Casadesus
 * <p>
 * This file can not be copied and/or distributed without the express
 * permission of Jaume Casadesus
 * </p>
 * 
 * <p>Re-Implemented in July 2012 to remove dependence from JAI
 * With this version BreedPix does not require installing JAI.</p>
 * 
 * <p>Modified by George El-Haddad (george.dma@gmail.com) to work with
 * CIMMY Maize project, with permission from Jaume Casadesus.</p>
 * 
 * @author Jaume Casadesus (jaume.casadesus@irta.cat)
 * 
 * 
 */
public class PicVIOperation {

        private static final float INV_255 = 1.0f / 255.0f;
        private static final float MIN_H_GA = 60;
        private static final float MAX_H = 180;
        private static final float MIN_SAT = 1;
        private static final float MIN_H_GGA = 80;
        
        private static float u_n = 4.0f / (1.0f + 15.0f + 3.0f);
        private static float v_n = 9.0f / (1.0f + 15.0f + 3.0f);

        public PicVIOperation() {
                
        }
        
        public final BreedPixResult execute(RenderedImage inputImage) {
                
                BreedPixResult result = new BreedPixResult();

                // initialize data for averaging colors
                double R = 0;
                double G = 0;
                double B = 0;
                int count = 0;
                int countGA = 0;
                int countGGA = 0;
                
                // create a PixelMask for GA and another for GGA
                PixelMask maskGA = new PixelMask(inputImage, false);
                PixelMask maskGGA = new PixelMask(inputImage, false);

                // scan the image
                Raster raster = inputImage.getData();
                Rectangle rect = raster.getBounds();
                int minX = (int) rect.getMinX();
                int maxX = (int) rect.getMaxX();
                int minY = (int) rect.getMinY();
                int maxY = (int) rect.getMaxY();

                ColorSpace colorSpace = inputImage.getColorModel().getColorSpace();

                float[] rgbCoords = new float[3];
                float[] hsiCoords = new float[3];

                float minHGA = MIN_H_GA;
                float maxHGA = MAX_H;
                float minSatGA = MIN_SAT / 100f;
                float minHGGA = MIN_H_GGA;

                for (int x = minX; x < maxX; x++) {
                        for (int y = minY; y < maxY; y++) {
                                // at every single pixel, get color
                                rgbCoords = raster.getPixel(x, y, rgbCoords);
                                rgbCoords[0] *= INV_255;
                                rgbCoords[1] *= INV_255;
                                rgbCoords[2] *= INV_255;
                                
                                R += rgbCoords[0];
                                G += rgbCoords[1];
                                B += rgbCoords[2];

                                // convert color to HSI
                                hsiCoords = RGBtoHSI(rgbCoords);

                                // determine if it belongs to GA
                                if (hsiCoords[0] >= minHGA && hsiCoords[0] <= maxHGA) {
                                        if (hsiCoords[1] >= minSatGA) {
                                                maskGA.set(x, y, true);
                                                countGA++;
                                                // determine if it belongs to GGA
                                                if (hsiCoords[0] > minHGGA) {
                                                        maskGGA.set(x, y, true);
                                                        countGGA++;
                                                }
                                        }
                                }
                                
                                count++;
                        }
                }
                
                float[] meanRGB = new float[] {
                                                (float) (R / count),
                                                (float) (G / count),
                                                (float) (B / count)
                                              };

                // convert meanRGB into HSI
                hsiCoords = RGBtoHSI(meanRGB);

                // convert meanRGB to CIE_XYZ
                float[] xyzCoords = colorSpace.toCIEXYZ(meanRGB);

                // convert meanRGB to CIELab
                float[] LabCoords = CIEXYZtoLab(xyzCoords, new float[3]);

                // convert meanRGB to CIELuv
                float[] LuvCoords = CIEXYZtoLuv(xyzCoords, new float[3]);
                
                //TODO use a logger for debugging
//                System.out.println("meanRGB=[" + meanRGB[0] + "," + meanRGB[1] + "," + meanRGB[2] + "]");
//                System.out.println("HSB=[" + hsiCoords[0] + "," + hsiCoords[1] + "," + hsiCoords[2] + "]");
//                System.out.println("XYZ=[" + xyzCoords[0] + "," + xyzCoords[1] + "," + xyzCoords[2] + "]");
//                System.out.println("Lab=[" + LabCoords[0] + "," + LabCoords[1] + "," + LabCoords[2] + "]");
//                System.out.println("Luv=[" + LuvCoords[0] + "," + LuvCoords[1] + "," + LuvCoords[2] + "]");
//                System.out.println("minHGA=" + minHGA + ", maxHGA=" + maxHGA + ", minSatGA=" + minSatGA);
//                System.out.println("countGA=" + countGA + ", countGGA=" + countGGA + ", counts=" + count);

                result.setGa_roi(maskGA);
                result.setGa(countGA * 1.0d / count);
                result.setGga_roi(maskGGA);
                result.setGga(countGGA * 1.0d / count);
                result.setIhs_h(hsiCoords[0]);
                result.setIhs_s(hsiCoords[1]);
                result.setIhs_i(hsiCoords[2]);
                result.setLab_l(LabCoords[0]);
                result.setLab_a(LabCoords[1]);
                result.setLab_b(LabCoords[2]);
                result.setLuv_u(LuvCoords[1]);
                result.setLuv_v(LuvCoords[2]);

                return result;
        }
        
        private static final float[] RGBtoHSI(float[] rgb) {
                float M = 0;
                float m = 1;
                float maxI = -1;
                float I = 0;
                float H = 0;
                float S = 0;

                for (int i = 0; i < 3; i++) {
                        if (rgb[i] > M) {
                                M = rgb[i];
                                maxI = i;
                        }
                        
                        if (rgb[i] < m) {
                                m = rgb[i];
                        }
                }
                
                float chr = M - m;
                if (maxI == 0) {
                        H = ((rgb[1] - rgb[2]) / chr) % 6;
                }
                
                if (maxI == 1) {
                        H = ((rgb[2] - rgb[0]) / chr) + 2;
                }
                
                if (maxI == 2) {
                        H = ((rgb[0] - rgb[1]) / chr) + 4;
                }
                
                H *= 60;
                I = (rgb[0] + rgb[1] + rgb[2]) / 3;
                
                if (chr == 0) {
                        S = 0;
                }
                else {
                        S = 1 - m / I;
                }
                
                return new float[] { H, S, I };
        }

        /**
         * Converts one pixel from XYZ to Lab
         * 
         * @param pixel
         * @return
         */
        private static final float[] CIEXYZtoLab(float[] xyz, float[] Lab) {
                double[] fXYZ = new double[3];
                for (int i = 0; i < 3; i++) {
                        if (xyz[i] > 0.008856) {
                                fXYZ[i] = Math.pow(xyz[i], 1.0 / 3.0);
                        }
                        else {
                                fXYZ[i] = 7.787 * xyz[i] + 16 / 116;
                        }
                }
                
                Lab[0] = (float) (116.0 * fXYZ[1] - 16.0);
                Lab[1] = (float) (500.0 * (fXYZ[0] - fXYZ[1]));
                Lab[2] = (float) (200.0 * (fXYZ[1] - fXYZ[2]));
                return Lab;
        }
        
        private static final float[] CIEXYZtoLuv(float[] xyz, float[] Luv) {
                float denom = (xyz[0] + 15 * xyz[1] + 3 * xyz[2]);
                Luv[0] = (float) (116.0 * Math.pow(xyz[1], 1.0 / 3.0) - 16.0);
                Luv[1] = 4 * xyz[0] / denom;
                Luv[2] = 9 * xyz[1] / denom;
                Luv[1] = 13 * Luv[0] * (Luv[1] - u_n);
                Luv[2] = 13 * Luv[0] * (Luv[2] - v_n);
                return Luv;
        }

        private static final void showColor(String colorname, float[] rgb) {
                
                float[] rgbx = new float[3];
                for (int i = 0; i < 3; i++) {
                        rgbx[i] = rgb[i] / 255;
                }
                
                ColorSpace CIEXYZ = ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);

                // convert color to IHS
                float[] hsb = RGBtoHSI(rgb);

                // convert color to CIE_XYZ
                float[] xyz = CIEXYZ.fromRGB(rgb);

                // convert color to Lab
                float[] Lab = CIEXYZtoLab(xyz, new float[3]);

                // convert color to Luv
                float[] Luv = CIEXYZtoLuv(xyz, new float[3]);
                
                System.out.println("\n" + colorname);
                System.out.println("rgb=[" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + "]");
                System.out.println("HSB=[" + hsb[0] + ", " + hsb[1] + ", " + hsb[2] + "]");
                System.out.println("XYZ=[" + xyz[0] + ", " + xyz[1] + ", " + xyz[2] + "]");
                System.out.println("Lab=[" + Lab[0] + ", " + Lab[1] + ", " + Lab[2] + "]");
                System.out.println("Luv=[" + Luv[0] + ", " + Luv[1] + ", " + Luv[2] + "]");
        }
        
        //Test Stub
        public static void main(String[] args) {
                float[] RED = new float[]   { 255, 0, 0 };
                float[] GREEN = new float[] { 0, 255, 0 };
                float[] BLUE = new float[]  { 0, 0, 255 };
                float[] GREY = new float[]  { 0.10f, 0.10f, 0.10f };
                float[] WHITE = new float[] { 255, 255, 255 };

                showColor("RED", RED);
                showColor("GREEN", GREEN);
                showColor("BLUE", BLUE);
                showColor("GREY", GREY);
                showColor("WHITE", WHITE);
        }
}
