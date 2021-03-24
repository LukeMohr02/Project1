package orm.commands.table.alter;

import org.jetbrains.annotations.NotNull;
import orm.commands.table.Update;

public class DropColumn implements Comparable<DropColumn>  {
    String columnName;

    public DropColumn(String columnName) {
        this.columnName =  columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    @Override
    public int compareTo(@NotNull DropColumn d) {
        int i = columnName.compareTo(d.columnName);
        if (i != 0) return i;

        return 0;
    }
}
