package gym;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public static int WIDTH;
    public static int HEIGHT;
    private World gym;

    public GamePanel(World gym) {
        WIDTH = World.WIDTH;
        HEIGHT = World.HEIGHT;
        this.gym = gym;

        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Courier New", Font.BOLD, 25));
        g.drawString(Double.toString(gym.getScore()/33f), getWidth()/2 - 5, 100);
        gym.render(g);
    }
}
