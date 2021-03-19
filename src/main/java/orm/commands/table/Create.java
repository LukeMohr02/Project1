package orm.commands.table;

import orm.commands.Column;

import java.util.List;

public class Create {
    List<Column> columns;

    public Create(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }
}
