public class LackOfValueException extends ValueException
{
    public LackOfValueException(String missing)
    {
        super("Brak wartości dla klucza " + missing);
    }
}
