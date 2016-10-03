package util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Oleksii on 02.10.2016.
 * oleksii.holyk@outlook.com
 */
public class DbConnection {

    public String getPropertyValue(String key) {
        Properties properties = new Properties();
        String value = "";
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }


    /*
db.driver_class=com.mysql.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/tame
db.username=root
db.password=root
  * */

    public Connection setConnection(){
        Connection connection = null;
        try {
            Class.forName(getPropertyValue("db.driver_class"));
            connection = DriverManager.
                    getConnection(getPropertyValue("db.url"), getPropertyValue("db.username"), getPropertyValue("db.password"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
