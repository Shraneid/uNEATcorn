package gym;

import neat.Client;
import neat.Neat;
import visual.Frame;

public class Main {
    private static final int MAX_EPOCHS = 500;

    public static void main(String[] args) throws InterruptedException{
        Dino.loadImages();
        gym.World.setFPS(30);

        //NEAT ALGORITHM FOR DINO
        Neat neat = new Neat(3,1,1000);
        World env = new World(neat.clients.size());

        Client c;
        Dino d;

        env.CreateAndShowGUI();
        Frame f = new Frame(neat.getBestGenome());

        double[] in = new double[neat.getInputSize()];
        double score = 0;

        for(int i = 0; i < MAX_EPOCHS; i++) {
            env.reset();

            neat.sortSpecies();
            neat.printSpecies();

            int count = 0;
            while (env.someAreAlive() && count < 1000) {
                env.step();
                if (env.showing()) {
                    env.render();
                }

                //CREATE INPUTS
                in[0] = (double)env.getDistanceToClosestObstacle()/(double)World.WIDTH;
                in[1] = env.getSpeed()/25;

                for (int j = 0; j < neat.clients.size(); j++) {
                    c = neat.getClient(j);
                    d = env.getDinoAtIndex(j);
                    in[2] = ((double)env.GROUND_Y - (double)d.getY()) / 150f;

                    double output = c.calculate(in)[0];

                    if (output > 0.5){
                        d.jump();
                    }
                }
                count++;
            }

            for (int j = 0; j < neat.clients.size(); j++) {
                c = neat.getClient(j);
                d = env.getDinoAtIndex(j);

                c.setScore((d.getScore())/30);
            }

            neat.evolve();
            f.setGenome(neat.getBestGenome());

            score = Math.max(neat.getBestScore(), score);
            System.out.println((double)i/MAX_EPOCHS*100f + "%");

            //IF IT GETS BETTER THAN 95% OF MAXIMUM SCORE WE END TRAINING
            if (score > (double)env.MAX_SCORE * 0.95f){
                System.out.println("BRAVO");
                System.out.println("Finished in " + i + " epochs");
                i = MAX_EPOCHS;
            }
        }
        neat.printSpecies();

        //JUST RUNNING THE EVOLVED POPULATION OVER AND OVER WHEN TRAINING IS OVER
        while (true){
            env.reset();
            while (env.someAreAlive()) {
                env.step();
                if (env.showing()) {
                    env.render();
                }

                in[0] = (double)env.getDistanceToClosestObstacle()/(double)World.WIDTH;
                in[1] = env.getSpeed()/25;
                for (int j = 0; j < neat.clients.size(); j++) {
                    c = neat.getClient(j);
                    d = env.getDinoAtIndex(j);
                    in[2] = ((double)env.GROUND_Y - (double)d.getY()) / 150f;

                    double output = c.calculate(in)[0];

                    if (output > 0.5){
                        d.jump();
                    }
                }
            }
        }
    }
}
