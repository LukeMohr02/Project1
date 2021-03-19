package orm.commands.table;

import orm.commands.table.alter.Add;
import orm.commands.table.alter.Constraint;
import orm.commands.table.alter.DropColumn;
import orm.commands.table.alter.Type;

public class Alter {
    Add add;
    DropColumn drop;
    Type type;
    Constraint constraint;

    public Add getAdd() {
        return add;
    }

    public void setAdd(Add add) {
        this.add = add;
    }

    public DropColumn getDropColumn() {
        return drop;
    }

    public void setDropColumn(DropColumn drop) {
        this.drop = drop;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }
}
