package gym;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class World {
    JFrame mainWindow = new JFrame("uNEATcorn");
    private GamePanel gamePanel;

    public static final int MAX_SCORE = 30;

    public static final int ANIMATION_FRAMES = 3;
    public static int FPS = 30;
    public static int frameDuration = 1000/FPS;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    public static final int MENU_HEIGHT = 100;
    public static final int GROUND_Y = 375;
    public static final int SPACING = 300;

    private int population_size;
    private ArrayList<Dino> population = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    private final double BASE_SPEED = 6;

    private double speed = BASE_SPEED;
    private double score = 1;

    private Dino d;
    private boolean shown = true;

    public double getScore() {
        return score;
    }

    public double getSpeed() {
        return speed;
    }

    public World(int population_size) {
        this.population_size = population_size;
        createPopulation();
        createObstacles();
    }

    public void step() {
        //this.speed = Math.random();
        if (score % 100 == 0){
            this.speed *= 1.1;
        }

        //ADDING OBSTACLES
        if (obstacles.get(obstacles.size()-1).getX() < WIDTH - SPACING){
            obstacles.add(new Obstacle(WIDTH));
            /*if (Math.random() < 0.5) {
                obstacles.add(new Obstacle(WIDTH));
            } else if (Math.random() < 0.5){
                obstacles.add(new Obstacle(WIDTH));
                obstacles.add(new Obstacle(WIDTH + obstacles.get(obstacles.size()-1).width));
            } else {
                obstacles.add(new Obstacle(WIDTH));
                obstacles.add(new Obstacle(WIDTH + obstacles.get(obstacles.size()-1).width));
                obstacles.add(new Obstacle(WIDTH + 2*obstacles.get(obstacles.size()-1).width));
            }*/
        }

        //UPDATING OBSTACLES AND DINOS
        int size = obstacles.size();
        for(int i = size - 1; i >= 0; i--){
            obstacles.get(i).update(obstacles, speed);
        }

        obstacles.sort(Comparator.comparingInt(Obstacle::getX));
        size = population.size();

        for(int i = 0; i < size; i++){
            d = population.get(i);
            if (d.isAlive()) {
                d.update(obstacles.get(0));
            }
        }

        score++;
    }

    public void reset(){
        speed = BASE_SPEED;
        score = 1;
        createPopulation();
        createObstacles();
    }

    private void createPopulation() {
        population.clear();
        for (int i = 0; i < population_size; i++){
            population.add(new Dino());
        }
    }

    private void createObstacles() {
        obstacles.clear();
        obstacles.add(new Obstacle(WIDTH));
    }

    public int getDistanceToClosestObstacle(){
        if (obstacles.get(0).getX() < Dino.x){
            return obstacles.get(1).getX();
        }
        return obstacles.get(0).getX();
    }

    public void render(Graphics g) {
        int size = obstacles.size();
        for (int i = size - 1; i >= 0; i--){
            try{
                obstacles.get(i).create(g);
            } catch (Exception e){
                System.out.println(e);
            }
        }

        size = population.size();
        for (int i = 0; i < size; i++){
            if (population.get(i).isAlive()) {
                population.get(i).create(g);
            }
        }
    }

    public void render() throws InterruptedException {
        Thread.sleep(World.frameDuration);
        this.mainWindow.repaint();
    }

    public void CreateAndShowGUI(){
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = mainWindow.getContentPane();
        this.gamePanel = new GamePanel(this);
        gamePanel.setFocusable(true);
        container.setLayout(new BorderLayout());
        container.add(gamePanel, BorderLayout.CENTER);
        mainWindow.setSize(WIDTH, HEIGHT + MENU_HEIGHT);
        mainWindow.setResizable(false);

        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        try{
            UIManager.setLookAndFeel(looks[3].getClassName());
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (InstantiationException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e){
            e.printStackTrace();
        }

        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(WIDTH,MENU_HEIGHT));
        menu.setLayout(new GridLayout(1,6));

        JButton render = new JButton("RENDER / STOP RENDER");
        render.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hide_show();
            }
        });
        menu.add(render);

        JButton ff = new JButton("FAST FORWARD / SLOW DOWN");
        ff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(World.FPS < 500) {
                    World.setFPS(1000);
                } else {
                    World.setFPS(30);
                }
            }
        });
        menu.add(ff);

        container.add(menu, BorderLayout.NORTH);
        mainWindow.setVisible(true);
    }

    public boolean someAreAlive(){
        for (Dino d : population){
            if (d.isAlive()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Dino> getPopulation() {
        return population;
    }

    public static void setFPS(int FPS) {
        World.FPS = FPS;
        World.frameDuration = 1000/FPS;
    }

    public boolean showing() {
        return this.shown;
    }

    public void hide_show(){
        this.shown = !this.shown;
    }

    public Dino getDinoAtIndex(int index) {
        return population.get(index);
    }
}
