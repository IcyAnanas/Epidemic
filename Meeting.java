import java.util.Random;

public class Meeting
{
    private Agent a;
    private Agent b;

    public Meeting(Agent a, Agent b)
    {
        this.a = a;
        this.b = b;
    }

    public void carryOut(Epidemic e, Society s, Random r)
    {
        if(a.isAlive() && b.isAlive())
        {
            if( ( r.nextDouble() ) <= e.getProbOfInfection() )
            {
                if( ( a.isInfected() && b.infect() ) || ( b.isInfected() && a.infect() ) )
                {
                    s.increaseInfected(1);
                    s.decreaseHealthy(1);
                }
            }
        }
    }
}
