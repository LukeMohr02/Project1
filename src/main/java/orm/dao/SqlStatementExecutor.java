package orm.dao;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

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
//        System.out.println(statement);

        // Query statement
        if (statement.contains("select")) {

            try (Connection c = ConnectionPool.getConnection();
                PreparedStatement ps = c.prepareStatement(statement);
                ResultSet rs = ps.executeQuery()) {

                StringBuilder sb;
                FileWriter writer = new FileWriter("src/main/java/user/json/SerializedObjects.txt", true);

                while (rs.next()) {
                    sb = new StringBuilder("{");
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    for (int i = 0; i < columnCount; i++) {
                        sb.append("\"").append(rsmd.getColumnName(i + 1)).append("\": \"").append(rs.getObject(rsmd.getColumnName(i + 1))).append("\", ");
                    }

                    sb.delete(sb.length()-2, sb.length()-1);
                    sb.append("}");

                    writer.write(sb.toString() + System.lineSeparator());
                }

                writer.flush();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
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
