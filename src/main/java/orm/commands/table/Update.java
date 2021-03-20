package orm.commands.table;

import orm.commands.Column;
import orm.commands.table.condition.Condition;
import orm.exceptions.ColumnMismatchException;

import java.util.List;

public class Update {
    String[] columns;
    String[] values;
    Condition[] conditions;

    public Update(List<String> columns, String values, List<Condition> conditions) throws ColumnMismatchException {
        this.columns = (String[]) columns.toArray();
        this.values = values.replaceAll(" ", "").split(",");
        this.conditions = (Condition[]) conditions.toArray();

        if (this.values.length != this.columns.length) {
            throw new ColumnMismatchException(columns, this.values);
        }
    }
}
