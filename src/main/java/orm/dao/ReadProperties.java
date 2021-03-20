package orm.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ReadProperties {
    String[] result;
    InputStream inputStream;

    public String[] read() {

        try {
            Properties p = new Properties();
            String fileName = "database.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

            if (inputStream != null) {
                p.load(inputStream);
            } else {
                throw new FileNotFoundException("File not found: " + fileName);
            }

            String url = p.getProperty("JDBC-URL");
            String username = p.getProperty("Username");
            String password = p.getProperty("Password");
            return new String[]{url, username, password};

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
