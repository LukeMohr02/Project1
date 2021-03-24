package orm.commands.table;

import orm.commands.Column;
import orm.exceptions.ColumnMismatchException;

import java.util.List;

public class Insert implements Comparable<Insert>  {
    String[] columns;
    String[] values;

    public Insert(String columns, String values) throws ColumnMismatchException {
        this.columns = columns.replaceAll(" ", "").replaceAll("\n", "").split(",");
        this.values = values.replaceAll(" ", "").replaceAll("\n", "").split(",");

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

    @Override
    public int compareTo(Insert in) {
        assert columns.length == in.columns.length;
        for (int j = 0; j < columns.length; j++) {
            int i = columns[j].compareTo(in.columns[j]);
            if (i != 0) return i;
        }

        assert values.length == in.values.length;
        for (int j = 0; j < values.length; j++) {
            int i = values[j].compareTo(in.values[j]);
            if (i != 0) return i;
        }

        return 0;
    }
}
