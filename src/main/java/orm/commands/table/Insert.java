package orm.commands.table;

import orm.commands.Column;
import orm.exceptions.ColumnMismatchException;

import java.util.List;

public class Insert {
    String[] columns;
    String[] values;

    public Insert(String columns, String values) throws ColumnMismatchException {
        this.columns = columns.replaceAll(" ", "").split(",");
        this.values = values.replaceAll(" ", "").split(",");

        // TODO: this exception should be thrown whether or not there are parentheses () in values
//        System.out.println(this.values);
//        if (this.values.length != this.columns.length) {
//            throw new ColumnMismatchException(this.columns, this.values);
//        }
    }

    public Insert(String values) {
        this.columns = new String[0];
        this.values = values.replaceAll(" ", "").split(",");
    }

    public String[] getColumns() {
        return columns;
    }

    public String[] getValues() {
        return values;
    }
}
