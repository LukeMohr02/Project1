package orm.exceptions;

import orm.commands.Column;

import java.util.Arrays;
import java.util.List;

public class ColumnMismatchException extends Exception {

    public ColumnMismatchException(String[] columns, String[] values) {
        super("Columns and values must match respective number, type, and order (columns: "+Arrays.toString(columns) +", values:"+Arrays.toString(values)+")");
    }
}
