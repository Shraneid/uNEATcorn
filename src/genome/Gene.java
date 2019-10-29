package genome;

public class Gene {

    protected int innovation_number;

    public Gene(){}

    public Gene(int innovation_number) {
        this.innovation_number = innovation_number;
    }

    public int getInnovation_number() {
        return innovation_number;
    }

    public void setInnovation_number(int innovation_number) {
        this.innovation_number = innovation_number;
    }
}
