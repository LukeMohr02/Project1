package orm.exceptions;

public class InvalidTypeException extends Exception {

    public InvalidTypeException() {
        super("Cannot change existing column type to serial");
    }
}
