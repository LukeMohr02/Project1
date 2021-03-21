package orm.commands.table;

import orm.commands.table.condition.Condition;

import java.util.List;

public class Export {
    String[] columns;
    Condition[] conditions;

    public Export(String columns, List<Condition> conditions) {
        this.columns = columns.replaceAll(" ", "").split(",");
        this.conditions = conditions.toArray(new Condition[0]);
    }

    public String[] getColumns() {
        return columns;
    }

    public Condition[] getConditions() {
        return conditions;
    }
}
