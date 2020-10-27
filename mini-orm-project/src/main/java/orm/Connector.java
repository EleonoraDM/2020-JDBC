package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";

    private static Connection connection;


    public static void setUpConnection(String username, String password, String dbName) {

        try {
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);

            connection = DriverManager.getConnection(DB_URL + dbName, props);
            System.out.println("Connected successfully!");
        } catch (SQLException e) {
            System.out.println("Missing credentials!!!");
            e.printStackTrace();
        }

    }

    public static Connection accessConnection() {
        return connection;
    }


}
