package gym;

import resource.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Dino {
    public static int ANIMATION_FRAMES = World.ANIMATION_FRAMES;

    /*private static BufferedImage sprite1 = new Resource().getResourceImage("../images/walk1.png");
    private static BufferedImage sprite2 = new Resource().getResourceImage("../images/walk2.png");*/
    public static ArrayList<BufferedImage> sprites = new ArrayList<>();
    private static BufferedImage image;

    private Iterator iterator = sprites.iterator();

    public static final int x = 100;
    private static final int height = 42;
    private static final int width = 45;
    private int topY;
    private int y;
    private boolean alive;

    private double velocity = 10;
    private double max_velocity = 20;
    private final double JUMP_FORCE = 20;

    private double score = 0;

    public Dino() {
        y = World.GROUND_Y;
        topY = y - 42;
        alive = true;
    }

    public void update(Obstacle o) {
        y += velocity;
        if (y > World.GROUND_Y){
            y = World.GROUND_Y;
        }
        velocity += 2;

        if (velocity > max_velocity){
            velocity = max_velocity;
        }

        topY = y - 42;

        //collide with object
        if ( (x > o.getX() && x<o.getX()+o.width && y>o.getY())   ||   (x+width > o.getX() && x+width < o.getX()+o.width && y > o.getY())){
            kill();
        }

        if (isAlive()) {
            score++;
        }
    }

    public void jump(){
        if (y == World.GROUND_Y) {
            velocity = -JUMP_FORCE;
        }
    }

    public static int getX() {
        return x;
    }
    public int getRealX(){
        return x+width;
    }
    public int getY() {
        return y;
    }
    public int getTopY() {
        return topY;
    }
    public boolean isAlive() {
        return alive;
    }
    public double getScore() {
        return score;
    }

    private void kill(){
        this.alive = false;
    }

    public void create(Graphics g) {
        g.drawImage(image, getX(), getTopY(), null);
        if (iterator.hasNext()) {
            image = (BufferedImage) iterator.next();
        } else {
            iterator = sprites.iterator();
            image = (BufferedImage) iterator.next();
        }
    }

    public static void loadImages() throws IOException {
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk1.png")));
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk2.png")));
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk3.png")));
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk4.png")));
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk5.png")));
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk6.png")));
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk7.png")));
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk8.png")));
        gym.Dino.sprites.add(ImageIO.read(new FileInputStream("resources/images/walk9.png")));
    }
}
