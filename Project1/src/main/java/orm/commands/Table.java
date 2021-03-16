package orm.commands;

import orm.commands.table.*;

public class Table {
    String schema;
    String name;
    boolean run;
    int priority;

    Create create;
    Alter alter;
    Drop drop;

    Insert insert;
    Export export;
    Update update;
    Delete delete;

    public Table() {

    }

}
