package orm.commands.table.alter;

import orm.commands.Column;

import java.util.List;

public class Add {
    Column[] columns;

    public Add(List<Column> columns) {
        System.out.println(columns.get(0).getName());
        this.columns = columns.toArray(new Column[0]);
    }

    public Column[] getColumns() {
        return columns;
    }

}
