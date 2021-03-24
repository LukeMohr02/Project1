package orm.commands.table.alter;

import org.jetbrains.annotations.NotNull;
import orm.commands.table.Update;

public class Constraint implements Comparable<Constraint>  {
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

    @Override
    public int compareTo(@NotNull Constraint c) {

        int i = columnName.compareTo(c.columnName);
        if (i != 0) return i;

        i = constraint.compareTo(c.constraint);
        if (i != 0) return i;

        return 0;
    }
}
