package cimmyt.maize.engine.breedpix;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Aug 15, 2015
 * <p>
 * This test class is still not final.
 * </p>
 */
public class PicVIOperationTest {
        
        private static final String PHOTOS_REP1_PATH = System.getProperty("user.dir")+File.separator+"test"+File.separator+"photos-rep1";
        
        @Test
        public void testImage() {
                BufferedImage bi = null;
                
                try {
                        bi = ImageIO.read(new File(PHOTOS_REP1_PATH+File.separator+"IMG_0922.JPG"));
                }
                catch (IOException e) {
                        e.printStackTrace();
                        fail();
                }
                
                assertNotNull(bi);
                
                //BufferedImage scaledRenderedImage = reduceToMaxSize(bi, 1024*768);
                
                //Image scaledImage = bi.getScaledInstance(1024, 768, Image.SCALE_DEFAULT);
                //BufferedImage scaledRenderedImage = getRenderedImage(scaledImage);
                
//                try {
//                        ImageIO.write(scaledRenderedImage, "JPG", new File(PHOTOS_REP1_PATH+File.separator+"IMG_0922_scaled_2.JPG"));
//                }
//                catch (IOException e) {
//                        e.printStackTrace();
//                        fail();
//                }
//                
                PicVIOperation picViOperation = new PicVIOperation();
//                BreedPixResult result = picViOperation.execute(bi);
//                System.out.println(result.toPrettyString());
                
                BufferedImage scaledRenderedImage = reduceToMaxSize(bi, 1024*768);
                BreedPixResult result = picViOperation.execute(scaledRenderedImage);
                System.out.println(result.toPrettyString());
        }
        
        
//        private BufferedImage getRenderedImage(Image in) {
//            int w = in.getWidth(null);
//            int h = in.getHeight(null);
//            int type = BufferedImage.TYPE_INT_RGB;
//            BufferedImage out = new BufferedImage(w, h, type);
//            Graphics2D g2 = out.createGraphics();
//            g2.drawImage(in, 0, 0, null);
//            g2.dispose();
//            return out;
//        }
        
        /**
         * Copyright 2012 Jaume Casadesus
         * <p>
         * This code block can not be copied and/or distributed without the express
         * permission of Jaume Casadesus
         * 
         * @author Jaume Casadesus (jaume.casadesus@irta.cat)
         */
        public BufferedImage reduceToMaxSize(BufferedImage image, int maxPixels) {
                int size = image.getHeight() * image.getWidth();

                double scale = 1.0D;
                BufferedImage rimg;
                if (size > maxPixels) {
                        scale = Math.sqrt(maxPixels * 1.0D / size);
                        rimg = scaleImage(image, scale);
                }
                else {
                        rimg = image;
                }
                return rimg;
        }
        
        /**
         * Copyright 2012 Jaume Casadesus
         * <p>
         * This code block can not be copied and/or distributed without the express
         * permission of Jaume Casadesus
         * 
         * @author Jaume Casadesus (jaume.casadesus@irta.cat)
         */
        public BufferedImage scaleImage(BufferedImage image, double scale) {
                return scaleImage(image, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
        }
        
        /**
         * Copyright 2012 Jaume Casadesus
         * <p>
         * This code block can not be copied and/or distributed without the express
         * permission of Jaume Casadesus
         * 
         * @author Jaume Casadesus (jaume.casadesus@irta.cat)
         */
        public BufferedImage scaleImage(BufferedImage image, int targetWidth, int targetHeight) {
                int imgType = image.getType();

                BufferedImage newImg = new BufferedImage(targetWidth, targetHeight, imgType);
                Graphics2D g2 = newImg.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(image, 0, 0, targetWidth, targetHeight, 0, 0, image.getWidth(), image.getHeight(), null);
                return newImg;
        }
}
