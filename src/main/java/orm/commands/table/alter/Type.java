package orm.commands.table.alter;

import orm.exceptions.InvalidTypeException;

public class Type {
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
}
