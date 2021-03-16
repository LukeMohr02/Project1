package orm.exceptions;

public class MultipleCreateTagsException extends Exception {

    public MultipleCreateTagsException() {
        super("Too many <create> tags, must be 1 or less");
    }
}
