package orm.commands.table.condition;

public class Condition {
    String column;
    String operator;
    String targetColumn;

    public Condition(String column, String operator, String targetColumn) {
        this.column = column;
        this.operator = operator;
        this.targetColumn = targetColumn;
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
}
