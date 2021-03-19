package orm.exceptions;

import java.util.List;

public class InvalidAttributeException extends Exception{

    public InvalidAttributeException(String attribute, String tag) {
        super("<"+tag+"> '"+attribute+"' attribute value is invalid.");

    }
}
