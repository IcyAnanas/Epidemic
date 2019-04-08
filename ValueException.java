public class ValueException extends Exception
{
    private String message;

    public ValueException(String message)
    {
        super();
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
