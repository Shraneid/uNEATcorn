package resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Resource {
    public BufferedImage getResourceImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
