package orm.exceptions;

public class NullContentException extends Exception{

    public NullContentException() {
        super("No text content found");
    }
}
