package orm.commands.table.alter;

public class Constraint {
    String name;

    public Constraint(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
