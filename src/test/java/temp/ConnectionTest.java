package temp;

import orm.dao.ConnectionPool;
import orm.dao.ReadProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args) throws SQLException {
        ReadProperties rp = new ReadProperties();
        String[] p = rp.read();

        for (String s : p) {
            System.out.println(s);
        }

        String query = "select * from animal";
        try (Connection c = ConnectionPool.getConnection();
             PreparedStatement ps = c.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getString("phylum"));
            }
        }
    }
}
