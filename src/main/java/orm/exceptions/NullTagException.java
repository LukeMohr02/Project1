package orm.exceptions;

public class NullTagException extends Exception{

    public NullTagException(String tag) {
        super("No <"+tag+"> tag found, there must be exactly 1");
    }
}
