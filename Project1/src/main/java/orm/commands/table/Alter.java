package orm.commands.table;

import orm.commands.table.alter.Add;
import orm.commands.table.alter.Constraint;
import orm.commands.table.alter.Type;

public class Alter {
    Add add;
    Drop drop;
    Type type;
    Constraint constraint;
}
