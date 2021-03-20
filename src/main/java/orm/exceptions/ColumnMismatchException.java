package orm.exceptions;

import orm.commands.Column;

import java.util.Arrays;
import java.util.List;

public class ColumnMismatchException extends Exception {

    public ColumnMismatchException(List<String> columns, String[] values) {
        super("Columns and values must match respective type and order (columns: "+Arrays.toString(columns.toArray()) +", values:"+Arrays.toString(values)+")");
    }
}
