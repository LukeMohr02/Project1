package orm.commands.table;

import orm.commands.Column;
import orm.commands.table.condition.Condition;
import orm.exceptions.ColumnMismatchException;

import java.util.List;

public class Update {
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
}
