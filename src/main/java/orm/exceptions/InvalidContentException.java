package orm.exceptions;

public class InvalidContentException extends Exception{

    public InvalidContentException() {
        super("Text content is invalid");
    }
}
