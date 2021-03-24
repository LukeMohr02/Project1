package orm.commands.table;

import orm.commands.table.condition.Condition;

import java.util.List;

public class Export implements Comparable<Export>  {
    String[] columns;
    Condition[] conditions;

    public Export(String columns, List<Condition> conditions) {
        this.columns = columns.replaceAll(" ", "").replaceAll("\n", "").split(",");
        this.conditions = conditions.toArray(new Condition[0]);
    }

    public String[] getColumns() {
        return columns;
    }

    public Condition[] getConditions() {
        return conditions;
    }

    public int compareTo(Export e) {
        assert columns.length == e.columns.length;
        for (int j = 0; j < columns.length; j++) {
            int i = columns[j].compareTo(e.columns[j]);
            if (i != 0) return i;
        }

        assert conditions.length == e.conditions.length;
        for (int j = 0; j < conditions.length; j++) {
            int i = conditions[j].compareTo(e.conditions[j]);
            if (i != 0) return i;
        }

        return 0;
    }
}
