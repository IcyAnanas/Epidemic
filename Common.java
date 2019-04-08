import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Common extends Agent
{
    public Common(int id, double probOfMeeting)
    {
        super(id, probOfMeeting);
    }

    private boolean drawMeeting(Day[] days, Random r)
    {
        double probOfM = probOfMeeting;
        if(isInfected()) probOfM /= 2;


        if(r.nextDouble() <= probOfM)
        {
            if(this.getFriendsNumber() > 0)
            {
                int whoToMeet = r.nextInt(friends.size());

                Agent toMeet = friends.get(whoToMeet);

                if(toMeet.condition != Condition.IMMUNE) days[r.nextInt(days.length)].addMeeting(new Meeting(this, toMeet));

                return true;
            }
        }
        return false;
    }

    public void drawMeetings(Day[] days, Random r)
    {
        if(this.condition != Condition.IMMUNE)  //inaczej spotkanie nie będzie miało żadnego efektu
        {
            while (drawMeeting(days, r));
        }
    }
}
