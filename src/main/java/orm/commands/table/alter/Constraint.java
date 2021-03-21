package orm.commands.table.alter;

public class Constraint {
    String columnName;
    String constraint;

    public Constraint(String columnName, String constraint) {
        this.columnName = columnName;
        this.constraint = constraint;

    }

    public String getColumnName() {
        return columnName;
    }

    public String getConstraint() {
        return constraint;
    }
}
