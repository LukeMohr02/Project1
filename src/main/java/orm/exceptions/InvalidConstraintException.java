package orm.exceptions;

public class InvalidConstraintException extends RuntimeException{

    public InvalidConstraintException(String msg) {
        super(msg);
    }
}
