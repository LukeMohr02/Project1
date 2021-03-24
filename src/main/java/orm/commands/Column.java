package orm.commands;

import org.jetbrains.annotations.NotNull;

public class Column implements Comparable<Column> {
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

        if (type.equals("auto-int")) {
            this.type = "serial";
        }
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

    @Override
    public int compareTo(@NotNull Column c) {
        boolean b;
        int i = name.compareTo(c.name);
        if (i != 0) return i;

        i = type.compareTo(c.type);
        if (i != 0) return i;

        b = notEmpty == c.notEmpty;
        if (!b) return 1;

        b = unique == c.unique;
        if (!b) return 1;

        b = primaryKey == c.primaryKey;
        if (!b) return 1;

        return 0;
    }
}
