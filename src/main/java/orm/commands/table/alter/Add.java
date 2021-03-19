package orm.commands.table.alter;

import orm.commands.Column;

import java.util.List;

public class Add {
    List<Column> columns;

    public Add(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

}
