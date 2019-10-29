package neat;

import calculations.Calculator;
import genome.Genome;

public class Client {

    private Calculator calculator;

    private Genome genome;
    private double score;
    private Species species;

    public void generateCalculator(){
        calculator = new Calculator(genome);
    }

    public double[] calculate(double... input){
        if (this.calculator == null){generateCalculator();}
        return this.calculator.calculate(input);
    }

    public double distance(Client other){
        return this.getGenome().distance(other.getGenome());
    }

    public void mutate(){
        getGenome().mutate();
    }

    public Calculator getCalculator() {
        return calculator;
    }
    public Genome getGenome() {
        return genome;
    }
    public void setGenome(Genome genome) {
        this.genome = genome;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public Species getSpecies() {
        return species;
    }
    public void setSpecies(Species species) {
        this.species = species;
    }
}
