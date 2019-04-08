import java.io.PrintWriter;
import java.util.*;

public class Society
{
    private int sizeOfPopulation;
    private int healthy;
    private int infected;
    private int immune;
    private int dead;
    private double probOfExtrovert;
    private double averageNumberOfFriends;
    private double probOfMeeting;
    private List<Agent> agents;

    private void setAttributes(int sizeOfPopulation, double probOfExtrovert, double averageNumberOfFriends, double probOfMeeting)
    {
        this.sizeOfPopulation = sizeOfPopulation;
        this.probOfExtrovert = probOfExtrovert;
        this.averageNumberOfFriends = averageNumberOfFriends;
        this.probOfMeeting = probOfMeeting;
        healthy = sizeOfPopulation - 1;
        infected = 1;
        immune = 0;
        dead = 0;
    }

    private void drawAgents(Random r)
    {
        agents = new ArrayList<>(sizeOfPopulation);

        double agentType;

        for(int i = 0; i < sizeOfPopulation; i++)
        {
            agentType = r.nextDouble();

            if(agentType <= probOfExtrovert)
            {
                agents.add(i, new Extrovert(i + 1, probOfMeeting));
            }
            else
            {
                agents.add(i, new Common(i + 1, probOfMeeting));
            }
        }

        agents.get(r.nextInt(sizeOfPopulation)).infect();
    }

    //tworzy listę wszystkich możliwych krawędzi (przyjaźni)
    //tasuje ją metodą shuffle
    //i zdejmuje pierwszych n (n == śrLiczbaZnajomych * liczbaAgentów) krawędzi
    //metoda dobra dla niedużych grafów (inaczej złożona czasowoi bardzo złożona pamięciowo - lista krawędzi jest kwadratowa względem liczby wierzchołków...)
    private void drawGraphOfConnections()
    {
        int sumOfFriends = (int)Math.round(sizeOfPopulation * averageNumberOfFriends);

        List<Edge> edges = new LinkedList<>();

        for(int i = 0; i < agents.size(); i++)
        {
            for(int j = i + 1; j < agents.size(); j++)
            {
                edges.add(new Edge(agents.get(i), agents.get(j)));
            }
        }

        Collections.shuffle(edges);

        for(int i = 0; i < sumOfFriends; i += 2)
        {
            edges.remove(0).setEdge();
        }
    }

    //metoda dobra, gdy grafy nie są "bliskie klice"
    //losuje jeden wierzchołek, aż wylosuje jakiś, który jeszcze nie ma wszystkich możliwych znajomych
    //losuje drugi wierzchołek, aż natrafi na jakiś, który jeszcze nie jest zaprzyjaźniony z pierwszym (i sam nie jest pierwszym)
    //chyba nie ma gwarancji rokładu normalnego (niektóre krawedzie mogą m
    private void drawGraph(Random r)
    {
        int sumOfFriends = (int)Math.round(sizeOfPopulation * averageNumberOfFriends);

        for(int i = 0; i < sumOfFriends; i += 2)
        {
            Agent a, b;

            while((a = agents.get(r.nextInt(agents.size()))).getFriendsNumber() == sizeOfPopulation - 1);
            while(a.isFriend(b = agents.get(r.nextInt(agents.size()))) || a == b);
            a.addFriend(b);
            b.addFriend(a);
        }
    }

    //metoda podobna do poprzedniej
    private void drawGrApH(Random r)
    {
        int sumOfFriends = (int)Math.round(sizeOfPopulation * averageNumberOfFriends);

        for(int i = 0; i < sumOfFriends; i += 2)
        {
            Agent a = null, b = null;   //inaczej kompilator daje warning, że a || b mogą być niezainicjalizowane...
            boolean edge_set = false;

            while(!edge_set)
            {
                a = agents.get(r.nextInt(agents.size()));
                b = agents.get(r.nextInt(agents.size()));
                if(a != b && !a.isFriend(b)) edge_set = true;
            }

            a.addFriend(b);
            b.addFriend(a);
        }

    }


    public Society(int sizeOfPopulation, double probOfExtrovert, double averageNumberOfFriends, double probOfMeeting, Random r)
    {
        setAttributes(sizeOfPopulation, probOfExtrovert, averageNumberOfFriends, probOfMeeting);

        drawAgents(r);

        //drawGrApH(r);
        //drawGraphOfConnections();
        drawGraph(r);
    }

    public void increaseInfected(int newly_infected)
    {
        infected += newly_infected;
    }

    public void decreaseHealthy(int newly_infected)
    {
        healthy -= newly_infected;
    }

    public void drawDeaths(Epidemic e, Random r)
    {
        Iterator<Agent> it = agents.iterator();
        Agent a;

        while(it.hasNext())
        {
            a = it.next();

            if(a.isInfected() && r.nextDouble() <= e.getProbOfDeath())
            {
                a.kill();
                it.remove();
                dead++;
                infected--;
            }
        }
    }

    public void drawRecoveries(Epidemic e, Random r)
    {
        Iterator<Agent> it = agents.iterator();
        Agent a;

        while(it.hasNext())
        {
            a = it.next();

            if(a.isInfected() && r.nextDouble() <= e.getProbOfRecovery())
            {
                a.cure();
                immune++;
                infected--;
            }
        }
    }

    public void drawMeetings(Day[] days, Random r)
    {
        Iterator<Agent> it = agents.iterator();

        for(Agent a: agents)
        {
            a.drawMeetings(days, r);
        }
    }

    public String Summary()
    {
        return healthy
                + " " + infected
                + " " + immune;
    }

    public void printGraph(PrintWriter writer)
    {
        writer.println("# graf");

        Iterator<Agent> it = agents.iterator();

        while(it.hasNext())
        {
            it.next().print(writer);
        }

        writer.println();
    }

    public void printAgents(PrintWriter writer)
    {
        writer.println("# agenci jako: id typ lub id* typ dla chorego");

        Iterator<Agent> it = agents.iterator();
        int i = 1;
        Agent a;

        while(it.hasNext())
        {
            writer.format("%d", i++);
            a = it.next();

            if(a.isInfected()) writer.format("*");

            if(a instanceof Extrovert)
            {
                writer.println(" towarzyski");
            }
            else
            {
                writer.println(" zwykły");
            }
        }

        writer.println();
    }
}
