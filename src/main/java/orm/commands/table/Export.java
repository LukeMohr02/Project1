package orm.commands.table;

import orm.commands.Column;
import orm.commands.table.condition.Condition;

import java.util.List;

public class Export {
    String[] columns;
    String[] conditions;

    public Export(List<Column> columns, List<Condition> conditions) {
        this.columns = (String[]) columns.toArray();
        this.conditions = (String[]) conditions.toArray();
    }
}
