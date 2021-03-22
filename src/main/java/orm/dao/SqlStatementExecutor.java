package orm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Connects to database, executes SQL statements
 */
public class SqlStatementExecutor {

    // Executes multiple SQL statements
    public void execute(String[] statements) throws SQLException {
        for (String s : statements) {
            execute(s);
        }
    }

    // Executes single SQL statement
    public void execute(String statement) throws SQLException {
        System.out.println(statement);

        // Query statement
        if (statement.contains("select")) {

            try (Connection c = ConnectionPool.getConnection();
                 PreparedStatement ps = c.prepareStatement(statement);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    // TODO: serialize query
                }
            }

        // Non-query statement
        } else {

            try (Connection c = ConnectionPool.getConnection();
                 PreparedStatement ps = c.prepareStatement(statement)) {

                int rowsUpdated = ps.executeUpdate();
            }

        }


    }
}
