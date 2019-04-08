import java.util.*;

public class Extrovert extends Agent
{
    public Extrovert(int id, double probOfMeeting)
    {
        super(id, probOfMeeting);
    }


    private Set<Agent> getAllFriends()
    {
        Set<Agent> allFriends = new HashSet<>(friends);

        Iterator<Agent> a = friends.iterator();
        Iterator<Agent> b;
        Agent temp;

        while(a.hasNext())
        {
            b = a.next().friends.iterator();

            while(b.hasNext())
            {
                temp = b.next();

                if(temp != this && !allFriends.contains(temp))
                {
                    allFriends.add(temp);
                }
            }
        }
        
        return allFriends;
    }

    private boolean drawMeeting(Day[] days, Collection<Agent> friendsToMeet, Random r)
    {
        if( r.nextDouble() <= probOfMeeting )
        {
            if(!this.isInfected())
            {
                if(friendsToMeet.size() > 0)
                {
                    int whoToMeet = r.nextInt(friendsToMeet.size());

                    Iterator<Agent> it = friendsToMeet.iterator();

                    while(whoToMeet-- > 1)
                    {
                        it.next();
                    }

                    //Agent toMeet = friendsToMeet.get(whoToMeet); wersja dla listy

                    days[r.nextInt(days.length)].addMeeting(new Meeting(this, it.next()));

                    return true;
                }
            }
            else
            {
                List<Agent> s = this.getFriends();

                if(s.size() > 0)
                {
                    Agent toMeet = s.get(r.nextInt(s.size()));

                    if(toMeet.condition != Condition.IMMUNE) days[r.nextInt(days.length)].addMeeting(new Meeting(this, toMeet));

                    return true;
                }
            }
        }
        return false;
    }

    public void drawMeetings(Day[] days, Random r)
    {
        if(this.condition != Condition.IMMUNE)  //inaczej spotkanie nie będzie miało żadnego efektu
        {
            Collection<Agent> friendsToMeet = getAllFriends();

            while(drawMeeting(days, friendsToMeet, r)) ;
        }
    }
}
