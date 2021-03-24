package orm.commands.table;

import orm.commands.table.condition.Condition;

import java.util.List;

public class Delete implements Comparable<Delete>  {
    Condition[] conditions;

    public Delete(List<Condition> conditions) {
        this.conditions = conditions.toArray(new Condition[0]);
    }

    public Condition[] getConditions() {
        return conditions;
    }

    public int compareTo(Delete d) {
        assert conditions.length == d.conditions.length;
        for (int j = 0; j < conditions.length; j++) {
            int i = conditions[j].compareTo(d.conditions[j]);
            if (i != 0) return i;
        }

        return 0;
    }
}
