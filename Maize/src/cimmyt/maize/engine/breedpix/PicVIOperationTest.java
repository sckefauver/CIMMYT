package cimmyt.maize.engine.breedpix;

import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Aug 15, 2015
 *
 */
public class PicVIOperationTest {
        
        private static final String PHOTOS_REP1_PATH = System.getProperty("user.dir")+File.separator+"test"+File.separator+"photos-rep1";
        
        @Test
        public void testImage() {
                BufferedImage bi = null;
                
                try {
                        bi = ImageIO.read(new File(PHOTOS_REP1_PATH+File.separator+"IMG_0921.JPG"));
                }
                catch (IOException e) {
                        e.printStackTrace();
                        fail();
                }
                
                assertNotNull(bi);
                
                PicVIOperation picViOperation = new PicVIOperation();
                BreedPixResult result = picViOperation.execute(bi);
                System.out.println(result.toString());
        }
}
