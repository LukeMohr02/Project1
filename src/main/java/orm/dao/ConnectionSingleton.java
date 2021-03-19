package orm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

    private static ConnectionSingleton instance;
    private static Connection connection;

    public static ConnectionSingleton getInstance(String connectionString, String username, String password) throws SQLException {
        if (instance == null) {
            instance = new ConnectionSingleton(connectionString, username, password);
        }
        return instance;
    }

    public ConnectionSingleton(String connectionString, String username, String password) throws SQLException {
        //jdbc:postgresql://<aws endpoint>:<port>/<database>
        //  Optional select schema: ?currentSchema=<schema name>
        connection = DriverManager.getConnection(connectionString, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
