package orm.commands.table;

import orm.commands.Column;
import orm.exceptions.ColumnMismatchException;

import java.util.List;

public class Insert {
    String[] columns;
    String[] values;

    public Insert(List<String> columns, String values) throws ColumnMismatchException {
        this.columns = (String[]) columns.toArray();
        this.values = values.replaceAll(" ", "").split(",");

        if (this.values.length != this.columns.length) {
            throw new ColumnMismatchException(columns, this.values);
        }
    }

    public Insert(String values) {
        this.columns = new String[0];
        this.values = values.replaceAll(" ", "").split(",");
    }
}
