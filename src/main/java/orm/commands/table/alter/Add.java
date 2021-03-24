package orm.commands.table.alter;

import org.jetbrains.annotations.NotNull;
import orm.commands.Column;
import orm.commands.table.Update;

import java.util.List;

public class Add implements Comparable<Add>  {
    Column[] columns;

    public Add(List<Column> columns) {
        this.columns = columns.toArray(new Column[0]);
    }

    public Column[] getColumns() {
        return columns;
    }

    @Override
    public int compareTo(@NotNull Add a) {

        assert columns.length == a.columns.length;
        for (int j = 0; j < columns.length; j++) {
            int i = columns[j].compareTo(a.getColumns()[j]);
            if (i != 0) return i;
        }

        return 0;
    }
}
