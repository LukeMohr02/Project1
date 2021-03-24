package orm.commands.table;

import org.jetbrains.annotations.NotNull;
import orm.commands.Column;
import orm.commands.Table;
import orm.commands.table.condition.Condition;
import orm.exceptions.ColumnMismatchException;

import java.util.List;

public class Update implements Comparable<Update> {
    String[] columns;
    String[] values;
    Condition[] conditions;

    public Update(String columns, String values, List<Condition> conditions) throws ColumnMismatchException {
        this.columns = columns.replaceAll(" ", "").split(",");
        this.values = values.replaceAll(" ", "").split(",");
        this.conditions = conditions.toArray(new Condition[0]);

        if (this.values.length != this.columns.length) {
            throw new ColumnMismatchException(this.columns, this.values);
        }
    }

    public String[] getColumns() {
        return columns;
    }

    public String[] getValues() {
        return values;
    }

    public Condition[] getConditions() {
        return conditions;
    }

    @Override
    public int compareTo(@NotNull Update u) {
        assert columns.length == u.columns.length;
        for (int j = 0; j < columns.length; j++) {
            int i = columns[j].compareTo(u.columns[j]);
            if (i != 0) return i;
        }

        assert values.length == u.values.length;
        for (int j = 0; j < values.length; j++) {
            int i = values[j].compareTo(u.values[j]);
            if (i != 0) return i;
        }

        assert conditions.length == u.conditions.length;
        for (int j = 0; j < conditions.length; j++) {
            int i = conditions[j].compareTo(u.conditions[j]);
            if (i != 0) return i;
        }

        return 0;
    }

}
