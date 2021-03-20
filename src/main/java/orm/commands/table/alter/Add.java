package orm.commands.table.alter;

import orm.commands.Column;

import java.util.List;

public class Add {
    Column[] columns;

    public Add(List<Column> columns) {
        this.columns = (Column[]) columns.toArray();
    }

    public Column[] getColumns() {
        return columns;
    }

}
