package orm.dao;

import orm.commands.*;
import orm.commands.table.*;
import orm.commands.table.alter.*;
import orm.commands.table.condition.*;
import orm.exceptions.InvalidConstraintException;

import java.util.List;
import java.util.ArrayList;

public class JavaToSql {
    String tableName;

    public String[] findCommands(Table table) {
        tableName = table.getName();
        List<String> commands = new ArrayList<>();

        /* DDL */
        // Drop command runs first
        if (table.getDrop() != null) {
            commands.add(formulateDrop(table.getDrop()));
        }

        if (table.getCreate() != null) {
            commands.add(formulateCreate(table.getCreate()));
        }

        if (!table.getAlter().isEmpty()) {
            for (Alter a : table.getAlter()) {
                if (a.getAdd() != null) {
                    commands.add(formulateAlterAdd(a.getAdd()));
                }
                if (a.getDropColumn() != null) {
                    commands.add(formulateAlterDropColumn(a.getDropColumn()));
                }
                if (a.getType() != null) {
                    commands.add(formulateAlterType(a.getType()));
                }
                if (a.getConstraint() != null) {
                    commands.add(formulateAlterConstraint(a.getConstraint()));
                }
            }
        }


        /* DML */
        if (!table.getInsert().isEmpty()) {
            for (Insert i : table.getInsert()) {
                commands.add(formulateInsert(i));
            }
        }

        if (!table.getUpdate().isEmpty()) {
            for (Update u : table.getUpdate()) {
                commands.add(formulateUpdate(u));
            }
        }

        if (!table.getDelete().isEmpty()) {
            for (Delete d : table.getDelete()) {
                commands.add(formulateDelete(d));
            }
        }

        // Export runs last
        if (!table.getExport().isEmpty()) {
            for (Export e : table.getExport()) {
                commands.add(formulateExport(e));
            }
        }

        return commands.toArray(new String[0]);
    }


    /* DDL */
    public String formulateCreate(Create create) {
        StringBuilder sql = new StringBuilder("create table "+tableName+"(");
        int primaryKeyCount = 0;

        for (Column c : create.getColumns()) {
            String constraints = getColumnConstraints(c);

            if (constraints.equals("primary key")) {
                primaryKeyCount++;

                if (primaryKeyCount > 1) {
                    throw new InvalidConstraintException("Multiple primary keys detected, there must be exactly 1");
                }
            }


            sql.append(c.getName()).append(" ").append(c.getType()).append(" ").append(constraints).append(", ");
        }

        sql.delete(sql.length()-2, sql.length()-1);
        sql.append(");");

        if (primaryKeyCount < 1) {
            throw new InvalidConstraintException("No primary key detected, there must be exactly 1");
        }

        return sql.toString();
    }

    public String formulateAlterAdd(Add add) {
        StringBuilder sql = new StringBuilder("alter table "+tableName+" ");

        for (Column c : add.getColumns()) {
            String constraints = getColumnConstraints(c);
            int primaryKeyCount = 0;

            if (constraints.equals("primary key")) {
                primaryKeyCount++;

                if (primaryKeyCount > 1) {
                    throw new InvalidConstraintException("Multiple primary keys detected, there cannot be more than 1");
                }
            }

            sql.append("add ").append(c.getName()).append(" ").append(c.getType()).append(" ").append(constraints).append(", ");
        }

        sql.delete(sql.length()-2, sql.length()-1);
        sql.append(";");

        return sql.toString();
    }

    public String formulateAlterDropColumn(DropColumn dropColumn) {
        return "alter table "+tableName+" drop column "+dropColumn.getColumnName()+";";
    }

    public String formulateAlterType(Type type) {
        return "alter table "+tableName+" alter column "+type.getColumnName()+" type "+type.getType()+";";
    }

    public String formulateAlterConstraint(Constraint constraint) {
        // TODO: add drop constraint, currently only add constraint
        StringBuilder sql = new StringBuilder("alter table "+tableName+" ");
        String columnName = constraint.getColumnName();

        switch (constraint.getConstraint()) {
            case "not-empty":
                sql.append("alter column "+columnName+" set not null;");
                break;
            case "unique":
                sql.append("add unique ("+columnName+");");
                break;
            case "unique-id":
                sql.append("add primary key ("+columnName+");");
                break;
        }

        return sql.toString();

    }

    public String formulateDrop(Drop drop) {
        return "drop table "+tableName+";";
    }


    /* DML */
    public String formulateInsert(Insert insert) {
        StringBuilder sql = new StringBuilder("insert into "+tableName+" (");

        for (String s : insert.getColumns()) {
            sql.append(s).append(", ");
        }

        sql.delete(sql.length()-2, sql.length()-1);
        sql.append(") values ");

        for (String s : insert.getValues()) {
            sql.append(s).append(", ");
        }

        sql.delete(sql.length()-2, sql.length()-1);
        sql.append(";");

        return sql.toString();
    }

    public String formulateExport(Export export) {
        StringBuilder sql = new StringBuilder("select ");
        String[] columns = export.getColumns();

        if (columns == null || columns.length < 1 || columns.length <= 1 && columns[0].equals("")) {
            sql.append("* ");
        } else {
            for (String s : columns) {
                sql.append(s).append(", ");
            }

            sql.delete(sql.length()-2, sql.length()-1);
        }

        sql.append("from "+tableName+" ");

        if (export.getConditions().length > 0) {
            sql.append("where ");

            for (Condition c : export.getConditions()) {
                sql.append(c.getColumn()).append(c.getOperator()).append(c.getTargetColumn()).append(" and ");
            }

            sql.delete(sql.length()-5, sql.length()-1);
        }

        sql.append(";");

        return sql.toString();
    }

    public String formulateUpdate(Update update) {
        StringBuilder sql = new StringBuilder("update "+tableName+" set ");

        for (int i = 0; i < update.getColumns().length; i++) {
            sql.append(update.getColumns()[i]).append(" = ").append(update.getValues()[i]).append(", ");
        }

        sql.delete(sql.length()-2, sql.length()-1);

        if (update.getConditions().length > 0) {
            sql.append(" where ");

            for (Condition c : update.getConditions()) {
                sql.append(c.getColumn()).append(" ").append(c.getOperator()).append(" ").append(c.getTargetColumn()).append(" and ");
            }

            sql.delete(sql.length()-5, sql.length()-1);
        }

        sql.append(";");

        return sql.toString();
    }

    public String formulateDelete(Delete delete) {
        StringBuilder sql = new StringBuilder("delete from "+tableName+" ");

        if (delete.getConditions().length > 0) {
            sql.append(" where ");

            for (Condition c : delete.getConditions()) {
                sql.append(c.getColumn()).append(" ").append(c.getOperator()).append(" ").append(c.getTargetColumn()).append(" and ");
            }

            sql.delete(sql.length()-5, sql.length()-1);
        }

        sql.append(";");

        return sql.toString();
    }

    public String getColumnConstraints(Column column) {
        boolean notNull = column.isNotEmpty();
        boolean unique = column.isUnique();
        boolean primaryKey = column.isPrimaryKey();

        if (primaryKey) {
            return "primary key";
        } else if (notNull && unique) {
            return "not null unique";
        } else if (notNull) {
            return "not null";
        } else if (unique) {
            return "unique";
        } else {
            return "";
        }

    }
}
