package orm.commands.table;

import orm.commands.table.alter.Add;
import orm.commands.table.alter.Constraint;
import orm.commands.table.alter.DropColumn;
import orm.commands.table.alter.Type;

public class Alter implements Comparable<Alter>  {
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

    public int compareTo(Alter a) {
        assert a.getAdd() != null;
        int i = add.compareTo(a.getAdd());
        if (i != 0) return i;

        assert a.getDropColumn() != null;
        i = drop.compareTo(a.getDropColumn());
        if (i != 0) return i;

        assert a.getType() != null;
        i = type.compareTo(a.getType());
        if (i != 0) return i;

        assert a.getConstraint() != null;
        i = constraint.compareTo(a.getConstraint());
        if (i != 0) return i;

        return 0;
    }
}
