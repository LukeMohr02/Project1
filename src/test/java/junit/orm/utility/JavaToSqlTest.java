package junit.orm.utility;

import junit.orm.command.GetTable;
import org.junit.Assert;
import org.junit.Test;
import orm.commands.Table;
import orm.dao.SqlStatementExecutor;
import orm.exceptions.ColumnMismatchException;
import orm.exceptions.InvalidTypeException;
import orm.utility.JavaToSql;

import java.sql.SQLException;

public class JavaToSqlTest {
    JavaToSql jts;
    Table dummyTable;
    String[] dummySql;

    public JavaToSqlTest() throws ColumnMismatchException, InvalidTypeException {
        jts = new JavaToSql();
        GetTable dt = new GetTable();
        dummyTable = dt.generateDummyTable();
        dummySql = GetTable.getDummyTableSql();
    }

    @Test
    public void findCommandsTest() {
        Assert.assertArrayEquals(jts.findCommands(new Table()), new String[0]);
        Assert.assertArrayEquals(jts.findCommands(dummyTable), dummySql);
    }
}
