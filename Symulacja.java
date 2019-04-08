import java.io.PrintWriter;
import java.util.Random;

public class Symulacja
{
    public static void main(String args[])
    {
        Parser p = new Parser();

        Epidemic e = p.getEpidemic();
        Society s = p.getSociety();
        Day[] days = p.getDays();
        Random r = p.getRandom();

        p.printValues();

        PrintWriter writer = p.getWriter();

        s.printAgents(writer);

        s.printGraph(writer);

        for(int i = 0; i < days.length; i++)
        {
            days[i] = new Day(i + 1);
        }

        writer.println("# liczność w kolejnych dniach");

        for(Day d: days)
        {
            d.carry(e, s, days, writer, r);
        }

        writer.close();
    }

}
