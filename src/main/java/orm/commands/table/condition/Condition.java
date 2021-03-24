package orm.commands.table.condition;

import org.jetbrains.annotations.NotNull;
import orm.commands.table.Update;

public class Condition implements Comparable<Condition>  {
    String column;
    String operator;
    String targetColumn;

    public Condition(String column, String operator, String targetColumn) {
        this.column = column;
        this.operator = operator;
        this.targetColumn = targetColumn;

        if (operator.equals("less")) {
            this.operator = "<";
        } else if (operator.equals("less=")) {
            this.operator = "<=";
        }
    }

    public String getColumn() {
        return column;
    }

    public String getOperator() {
        return operator;
    }

    public String getTargetColumn() {
        return targetColumn;
    }


    @Override
    public int compareTo(@NotNull Condition c) {
        int i = column.compareTo(c.column);
        if (i != 0) return i;

        i = operator.compareTo(c.operator);
        if (i != 0) return i;

        i = targetColumn.compareTo(c.targetColumn);
        if (i != 0) return i;

        return 0;
    }
}
