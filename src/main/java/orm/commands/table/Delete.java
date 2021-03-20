package orm.commands.table;

import orm.commands.table.condition.Condition;

import java.util.List;

public class Delete {
    Condition[] conditions;

    public Delete(List<Condition> conditions) {
        this.conditions = (Condition[]) conditions.toArray();
    }
}
