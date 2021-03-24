package orm.commands.table.alter;

import org.jetbrains.annotations.NotNull;
import orm.commands.table.Update;
import orm.exceptions.InvalidTypeException;

public class Type implements Comparable<Type>  {
    String columnName;
    String type;

    public Type(String columnName, String type) throws InvalidTypeException {
        this.columnName =  columnName;
        this.type = type;

        if (type.equals("auto-int")) {
            throw new InvalidTypeException();
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(@NotNull Type t) {
        int i = columnName.compareTo(t.columnName);
        if (i != 0) return i;

        i = type.compareTo(t.type);
        if (i != 0) return i;

        return 0;
    }
}
