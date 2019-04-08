public class InvalidValueException extends ValueException
{
    public InvalidValueException(String invalid_argument, String invalid_value)
    {
        super("Niedozwolona wartość \"" + invalid_value + "\" dla klucza " + invalid_argument);
    }
}
