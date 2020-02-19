package gym;

import resource.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Obstacle {

    private static BufferedImage image;

    static {
        try {
            image = ImageIO.read(new FileInputStream("resources/images/Cactus-1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int pad = image.getHeight();

    private int x;
    private int y = World.GROUND_Y - pad;

    public int height;
    public int width;

    public Obstacle(int x) {
        this.x = x;
        this.height = (int)Math.random()*25+25;
        this.height = pad;
        this.width = image.getWidth();
    }

    public void update(ArrayList<Obstacle> obstacles, double speed){
        x -= Math.round(speed);
        if (x + width < 0){
            obstacles.remove(this);
            //obstacles.add(new Obstacle((int) (World.WIDTH + Math.random()*25 + 25)));
        }
    }

    public void create(Graphics g) {
        g.drawImage(image, getX(), getY(), null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
