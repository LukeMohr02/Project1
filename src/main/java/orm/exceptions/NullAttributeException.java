package orm.exceptions;

public class NullAttributeException extends Exception{

    public NullAttributeException(String attribute) {
        super("No '"+attribute+"' attribute found, there must be exactly 1");
    }
}
