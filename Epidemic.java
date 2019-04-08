public class Epidemic
{
    private double probOfInfection;
    private double probOfRecovery;
    private double probOfDeath;

    public Epidemic(double probOfInfection, double probOfRecovery, double probOfDeath)
    {
        this.probOfDeath = probOfDeath;
        this.probOfInfection = probOfInfection;
        this.probOfRecovery = probOfRecovery;
    }

    public double getProbOfInfection()
    {
        return probOfInfection;
    }

    public double getProbOfDeath() { return probOfDeath; }

    public double getProbOfRecovery() { return probOfRecovery; }
}
