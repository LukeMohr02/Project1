package orm.commands.table.alter;

public class DropColumn {
    String columnName;

    public DropColumn(String columnName) {
        this.columnName =  columnName;
    }
}
