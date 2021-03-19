package orm.exceptions;

public class MultipleTagsException extends Exception {

    public MultipleTagsException(String tag) {
        super("Too many <"+tag+"> tags, there cannot be more than 1");
    }
}
