package orm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlStatementExecutor {

    public void execute(String[] statements) throws SQLException {
        for (String s : statements) {
            execute(s);
        }
    }

    public void execute(String statement) throws SQLException {
//        System.out.println(statement);

        if (statement.contains("select")) {

            try (Connection c = ConnectionPool.getConnection();
                 PreparedStatement ps = c.prepareStatement(statement);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    // TODO: map query to object
                }
            }

        } else {

            try (Connection c = ConnectionPool.getConnection();
                 PreparedStatement ps = c.prepareStatement(statement)) {

                int rowsUpdated = ps.executeUpdate();
            }

        }


    }
}
