package junit.orm.dao;

import org.junit.Test;
import orm.dao.SqlStatementExecutor;

import java.sql.SQLException;

public class SqlStatementExecutorTest {

    SqlStatementExecutor sse = new SqlStatementExecutor();

    @Test
    public void executeTest() throws SQLException {
        sse.execute(new String[]{"drop table testTable","create table testTable (C1 text, C2 int);","insert into testTable values ('text1',1)","select * from testTable"});
    }
}
