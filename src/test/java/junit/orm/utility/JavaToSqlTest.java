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
import java.util.Arrays;

public class JavaToSqlTest {
    JavaToSql jts;
    Table dummyTable;
    String[] dummySql;

    {
        jts = new JavaToSql();
        GetTable dt = new GetTable();
        dummyTable = dt.generateDummyTable();
        dummySql = GetTable.getDummyTableSql();
    }

    public JavaToSqlTest() throws ColumnMismatchException, InvalidTypeException {

    }

    @Test
    public void findCommandsTest() {
        Assert.assertArrayEquals(dummySql, jts.findCommands(dummyTable));
        Assert.assertArrayEquals(new String[0], jts.findCommands(new Table()));
    }
}
