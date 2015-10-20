package cimmyt.maize.engine.breedpix;

import java.awt.Rectangle;
import java.awt.image.RenderedImage;

/**
 * Copyright 2012 Jaume Casadesus
 * <p>
 * This file can not be copied and/or distributed without the express
 * permission of Jaume Casadesus
 * 
 * @author Jaume Casadesus (jaume.casadesus@irta.cat)
 *
 */
public class PixelMask {

        private boolean[][] mask;
        private int xmin;
        private int ymin;
        private int width;
        private int height;

        public PixelMask(RenderedImage image, boolean initializeAsTrue) {
                xmin = image.getMinX();
                ymin = image.getMinY();
                width = image.getWidth();
                height = image.getHeight();
                mask = new boolean[width][height];
                if (initializeAsTrue) {
                        for (int i = 0; i < width; i++) {
                                for (int j = 0; j < height; j++) {
                                        mask[i][j] = true;
                                }
                        }
                }
        }

        public final Rectangle getBounds() {
                return new Rectangle(xmin, ymin, width, height);
        }

        public final boolean contains(int x, int y) {
                int i = x - xmin;
                if (i > width) {
                        return false;
                }
                
                int j = y - ymin;
                if (j > height) {
                        return false;
                }
                
                boolean ret = false;
                
                try {
                        ret = mask[i][j];
                }
                catch (ArrayIndexOutOfBoundsException exc) {
                        exc.printStackTrace();
                }
                
                return ret;
        }

        public final void set(int x, int y, boolean isTrue) {
                int i = x - xmin;
                if (i < 0 || i > width) {
                        return;
                }
                
                int j = y - ymin;
                if (j < 0 || j > height) {
                        return;
                }
                
                mask[i][j] = isTrue;
        }

        public final int countPixels() {
                int count = 0;
                for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                                if (mask[i][j]) {
                                        count++;
                                }
                        }
                }
                
                return count;
        }
}
