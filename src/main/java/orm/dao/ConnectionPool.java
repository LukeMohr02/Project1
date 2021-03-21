package orm.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        ReadProperties rp = new ReadProperties();
        String[] properties = rp.read();

        config.setJdbcUrl(properties[0]);
        config.setUsername(properties[1]);
        config.setPassword(properties[2]);
        ds = new HikariDataSource(config);
    }

    private ConnectionPool() {

    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}
