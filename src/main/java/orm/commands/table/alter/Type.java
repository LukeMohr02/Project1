package orm.commands.table.alter;

public class Type {
    String columnName;
    String type;

    public Type(String columnName, String type) {
        this.columnName =  columnName;
        this.type = type;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getType() {
        return type;
    }
}
