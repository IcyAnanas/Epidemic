import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Agent
{
    protected Condition condition;
    private boolean alive;
    protected List<Agent> friends;
    protected double probOfMeeting;
    private int id;

    public Agent(int id, double probOfMeeting)
    {
        condition = Condition.HEALTHY;
        alive = true;
        friends = new ArrayList<>();
        this.id = id;
        this.probOfMeeting = probOfMeeting;
    }

    public boolean infect()
    {
        if(condition == Condition.HEALTHY)
        {
            condition = Condition.INFECTED;
            return true;
        }
        else return false;
    }

    public void cure()
    {
        condition = Condition.IMMUNE;
    }

    public void kill()
    {
        alive = false;

        Iterator<Agent> it = friends.iterator();
        Agent friend;

        while(it.hasNext())
        {
            friend = it.next();
            friend.removeFriend(this);
        }
    }

    private void removeFriend(Agent a)
    {
        friends.remove(a);
    }

    public void addFriend(Agent a)  //ten agent jeszcze nie jest przyjacielem this
    {
        friends.add(a);
    }

    public boolean isFriend(Agent a)
    {
        return friends.contains(a);
    }

    public boolean isAlive()
    {
        return alive;
    }

    public boolean isInfected()
    {
        return condition == Condition.INFECTED;
    }

    public List<Agent> getFriends()
    {
        return this.friends;
    }

    public int getFriendsNumber()
    {
        return friends.size();
    }

    public int getId()
    {
        return id;
    }

    public void print(PrintWriter writer)
    {
        writer.format("%d", this.id);

        Iterator<Agent> it = friends.iterator();

        while( it.hasNext() )
        {
            writer.format( " " + it.next().id );
        }

        writer.format("\n");
    }

    @Override
    public String toString()
    {
        return this.id + " "
                + "is alive: " + this.isAlive() + " "
                + this.condition;
    }


    abstract public void drawMeetings(Day[] days, Random r);
}
