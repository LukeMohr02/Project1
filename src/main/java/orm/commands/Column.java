package orm.commands;

public class Column {
    String name;
    String type;
    boolean notEmpty;
    boolean unique;
    boolean primaryKey;

    public Column(String name, String type, boolean notEmpty, boolean unique, boolean primaryKey) {
        this.name = name;
        this.type = type;
        this.notEmpty = notEmpty;
        this.unique = unique;
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isNotEmpty() {
        return notEmpty;
    }

    public boolean isUnique() {
        return unique;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }
}
