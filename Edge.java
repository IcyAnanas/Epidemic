public class Edge
{
    private Agent a;
    private Agent b;

    public Edge(Agent a, Agent b)
    {
        this.a = a;
        this.b = b;
    }

    public void setEdge()
    {
        a.addFriend(b);
        b.addFriend(a);
    }
}
