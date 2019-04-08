import java.io.*;
import java.nio.channels.Channels;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Random;


public class Parser
{
    private Society s;
    private Epidemic e;
    private Day[] days;

    private int seed;
    private int numberOfAgents;
    private double probOfExtrovert;
    private double probOfMeeting;
    private double probOfInfection;
    private double probOfRecovery;
    private double probOfDeath;
    private int numberOfDays;
    private double averageNumberOfFriends;
    private String reportFile;
    private PrintWriter writer;
    private Random r;

    public Parser()
    {
        parse();
        setSimulation();
    }

    private void setSimulation()
    {
        s = new Society(numberOfAgents, probOfExtrovert, averageNumberOfFriends, probOfMeeting, r);
        e = new Epidemic(probOfInfection, probOfRecovery, probOfDeath);
        days = new Day[numberOfDays];
    }

    public Society getSociety()
    {
        return s;
    }

    public Epidemic getEpidemic()
    {
        return e;
    }

    public Day[] getDays()
    {
        return days;
    }

    public Random getRandom()
    {
        return this.r;
    }

    public PrintWriter getWriter()
    {
        return this.writer;
    }

    private void parse()
    {
        String rootPath = null;

        try
        {
            rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        }
        catch(NullPointerException n)
        {
            System.out.println("getPath() się nie powiodło, oto komunikat wyjątku: " + n.getMessage());
            System.exit(1);
        }


        String defaultConfigPath = rootPath + "default.properties";
        String simulationConfigPath = rootPath + "simulation-conf.xml";

        FileInputStream fileInputStream = null;
        Reader reader;

        try
        {
            fileInputStream = new FileInputStream(defaultConfigPath);
        }
        catch(FileNotFoundException f)
        {
            System.out.println("Brak pliku default.properties");
            System.exit(2);
        }



        reader = Channels.newReader(fileInputStream.getChannel(), StandardCharsets.UTF_8.name());

        Properties defProps = new Properties();

        try
        {
            defProps.load(reader);
        }
        catch(MalformedInputException e)
        {
            System.out.println("Plik default.properties nie jest plikiem tekstowym (nie ma kodowania UTF-8)");
            System.exit(2);
        }
        catch(IOException io)
        {
            System.out.println("inputStreamReader nie działa, komunikat wyjątku: " + io.getMessage());
            System.exit(4);
        }


        Properties simProps = new Properties(defProps);

        try
        {
            simProps.loadFromXML(new FileInputStream(simulationConfigPath));
        }
        catch(FileNotFoundException f)
        {
            System.out.println("Brak pliku simulation-conf.xml");
            System.exit(3);
        }
        catch(MalformedInputException e)
        {
            System.out.println("Plik sim-conf.xml nie jest XML");
            System.exit(1);
        }
        catch(IOException io)
        {
            System.out.println("inputStreamReader nie działa, komunikat wyjątku: " + io.getMessage());
            System.exit(4);
        }

        try
        {
            setValues(simProps);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void setValues(Properties simProps) throws Exception
    {

        String seed = simProps.getProperty("seed");
        if(seed == null) throw new LackOfValueException("seed");

        String numberOfAgents = simProps.getProperty("liczbaAgentów");
        if(numberOfAgents == null) throw new LackOfValueException("liczbaAgentów");

        String probOfExtrovert = simProps.getProperty("prawdTowarzyski");
        if(probOfExtrovert == null) throw new LackOfValueException("prawdTowarzyski");

        String probOfMeeting = simProps.getProperty("prawdSpotkania");
        if(probOfMeeting == null) throw new LackOfValueException("prawdSpotkania");

        String probOfInfection = simProps.getProperty("prawdZarażenia");
        if(probOfInfection == null) throw new LackOfValueException("prawdZarażenia");

        String probOfRecovery = simProps.getProperty("prawdWyzdrowienia");
        if(probOfRecovery == null) throw new LackOfValueException("prawdWyzdrowienia");

        String probOfDeath = simProps.getProperty("śmiertelność");
        if(probOfDeath == null) throw new LackOfValueException("śmiertelność");

        String numberOfDays = simProps.getProperty("liczbaDni");
        if(numberOfDays == null) throw new LackOfValueException("liczbaDni");

        String averageFriendsNumber = simProps.getProperty("śrZnajomych");
        if(averageFriendsNumber == null) throw new LackOfValueException("śrZnajomych");

        String reportFile = simProps.getProperty("plikZRaportem");
        if(reportFile == null) throw new LackOfValueException("plikZRaportem");




        this.seed = parseInt("seed", seed);

        this.numberOfAgents = parseInt("liczbaAgentów", numberOfAgents);
        if(this.numberOfAgents < 1 || this.numberOfAgents > 10000000) throw new InvalidValueException("liczbaAgentów",numberOfAgents);

        this.probOfExtrovert = parseDouble("prawdTowarzyski", probOfExtrovert);

        this.probOfMeeting = parseDouble("prawdSpotkania", probOfMeeting);

        this.probOfInfection = parseDouble("prawdZarażenia", probOfInfection);

        this.probOfRecovery = parseDouble("prawdWyzdrowienia", probOfRecovery);

        this.probOfDeath = parseDouble("śmiertelność", probOfDeath);

        this.numberOfDays = parseInt("liczbaDni", numberOfDays);
        if(this.numberOfDays < 1 || this.numberOfDays > 1000) throw new InvalidValueException("liczbaDni", numberOfDays);

        this.averageNumberOfFriends = parseDouble("śrZnajomych", averageFriendsNumber);
        if(this.averageNumberOfFriends < 0 || this.averageNumberOfFriends > this.numberOfAgents - 1) throw new InvalidValueException("śrZnajomych", numberOfAgents);

        this.reportFile = reportFile;

        this.writer = new PrintWriter(reportFile, "UTF-8");

        this.r = new Random(this.seed);
    }


    private int parseInt(String nameOfValue, String s) throws Exception
    {
        int outcome;

        try
        {
            outcome = Integer.parseInt(s);
        }
        catch(Exception e)
        {
            throw new InvalidValueException(nameOfValue, s);
        }

        return outcome;
    }


    private double parseDouble(String nameOfValue, String s) throws Exception
    {
        double outcome;

        try
        {
            outcome = Double.parseDouble(s);
        }
        catch(Exception e)
        {
            throw new InvalidValueException(nameOfValue, s);
        }

        return outcome;
    }

    public void printValues()
    {
        writer.println("# twoje wyniki powinny zawierać te komentarze");

        writer.println("seed" + "=" + seed);
        writer.println("liczbaAgentów" + "=" + numberOfAgents);
        writer.println("prawdTowarzyski" + "=" + probOfExtrovert);
        writer.println("prawdSpotkania" + "=" + probOfMeeting);
        writer.println("prawdZarażenia" + "=" + probOfInfection);
        writer.println("prawdWyzdrowienia" + "=" + probOfRecovery);
        writer.println("śmiertelność" + "=" + probOfDeath);
        writer.println("liczbaDni" + "=" + numberOfDays);
        writer.println("śrZnajomych" + "=" + averageNumberOfFriends);

        writer.println();
    }
}
