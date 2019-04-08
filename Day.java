import java.io.PrintWriter;
import java.util.*;

public class Day
{
    private int number;
    private Collection<Meeting> meetings;

    public Day(int number)
    {
        this.number = number;
        meetings = new LinkedList<>();
    }

    public void carry(Epidemic e, Society s, Day[] days, PrintWriter writer, Random r)
    {
        s.drawDeaths(e, r);

        s.drawRecoveries(e, r);

        if(number < days.length) s.drawMeetings(Arrays.copyOfRange(days, number, days.length), r);

        Iterator<Meeting> it = meetings.iterator();

        while(it.hasNext())
        {
            it.next().carryOut(e, s, r);
            it.remove();
        }

        writer.println(s.Summary());
    }

    public void addMeeting(Meeting m)
    {
        meetings.add(m);
    }
}
