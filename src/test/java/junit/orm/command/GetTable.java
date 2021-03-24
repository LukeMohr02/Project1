package junit.orm.command;

import orm.commands.*;
import orm.commands.table.*;
import orm.commands.table.alter.*;
import orm.commands.table.condition.Condition;
import orm.exceptions.ColumnMismatchException;
import orm.exceptions.InvalidTypeException;

import java.util.ArrayList;
import java.util.List;

public  class GetTable {

    public static Table generateDummyTable() throws ColumnMismatchException, InvalidTypeException {
        Column column1 = new Column("column1", "text", false, false, true);
        Column column2 = new Column("column2", "int", true, false, false);
        Column column3 = new Column("column3", "auto-int", false, true, false);
        Column column4 = new Column("column4", "decimal", false, false, false);
        Column column5 = new Column("column5", "money", true, true, false);

        List<Column> columnList = new ArrayList<>();
        columnList.add(column1);
        columnList.add(column2);
        columnList.add(column3);
        columnList.add(column4);
        columnList.add(column5);

        List<Column> columnList2 = new ArrayList<>();
        columnList2.add(new Column("column6", "bool", false, false, false));

        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("column1", "=", "column1"));

        Create create = new Create(columnList);
        Alter alter = new Alter();
        Drop drop = new Drop();

        alter.setAdd(new Add(columnList2));
        alter.setDropColumn(new DropColumn(columnList.get(3).getName()));
        alter.setType(new Type(columnList.get(4).getName(), "money"));
        alter.setConstraint(new Constraint(columnList.get(4).getName(), "unique"));

        Table table = new Table();
        table.setName("TestTable");
        table.setSchema("schema");
        table.setCreate(create);
        table.addAlter(alter);
        table.setDrop(drop);

        table.addInsert(new Insert("column1,column2,column3,column5,column6","('text',1,0,0,false)"));
        table.addInsert(new Insert("column1,column2,column5", "('notnull',5,100)"));
        table.addExport(new Export("column1,column2", conditions));
        table.addUpdate(new Update("column2", "15", conditions));
        table.addDelete(new Delete(conditions));

        return table;
    }

    public static String[] getDummyTableSql() {
        return new String[] {
            "drop table TestTable;",
            "create table TestTable(column1 text primary key, column2 int not null, column3 serial unique, column4 decimal , column5 money not null unique );",
            "alter table TestTable add column6 bool  ;",
            "alter table TestTable drop column column4;",
            "alter table TestTable alter column column5 type money;",
            "alter table TestTable add unique (column5);",
            "insert into TestTable (column1, column2, column3, column5, column6 ) values ('text', 1, 0, 0, false) ;",
            "insert into TestTable (column1, column2, column5 ) values ('notnull', 5, 100) ;",
            "update TestTable set column2 = 15  where column1 = column1 ;",
            "delete from TestTable  where column1 = column1 ;",
            "select column1, column2 from TestTable where column1=column1 ;"
        };
    }

    public static Table generateDdlTable() throws InvalidTypeException {
        Column column1 = new Column("id", "auto-int", false, false, true);
        Column column2 = new Column("C1", "text", true, false, false);
        Column column3 = new Column("C2", "int", false, false, false);
        Column column4 = new Column("C3", "money", false, false, false);

        List<Column> columnList = new ArrayList<>();
        columnList.add(column1);
        columnList.add(column2);
        columnList.add(column3);
        columnList.add(column4);

        List<Column> columnList2 = new ArrayList<>();
        columnList2.add(new Column("CAdd", "text", false, false, false));

        Create create = new Create(columnList);
        Alter alter = new Alter();
        Drop drop = new Drop();

        alter.setAdd(new Add(columnList2));
        alter.setDropColumn(new DropColumn(column2.getName()));
        alter.setType(new Type(column3.getName(), "int"));
        alter.setConstraint(new Constraint(column4.getName(), "unique"));

        Table table = new Table();
        table.setName("example");
        table.setSchema("public");
        table.setCreate(create);
        table.addAlter(alter);
        table.setDrop(drop);

        return table;
    }

    public static Table generateDmlTable() throws ColumnMismatchException {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("C3", "=", "C3"));

        Insert insert = new Insert("C1,C3,CAdd", "('text1',6,'textA'),('text2',2,'textB'),('text3',3,'textC')");
        Export export = new Export("", new ArrayList<Condition>());
        Update update = new Update("C3,CAdd", "4,'updatedText'", conditions);
        Delete delete = new Delete(conditions);

        Table table = new Table();
        table.setName("example");
        table.setSchema("public");
        table.addInsert(insert);
        table.addExport(export);
        table.addUpdate(update);
        table.addDelete(delete);

        return table;
    }
}
