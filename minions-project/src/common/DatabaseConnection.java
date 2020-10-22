package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";

    public static Connection setUpConnection(String user, String password) throws SQLException {

        if (user.equals("") || user.isEmpty()) {
            throw new IllegalArgumentException("Invalid username!");
        }

        if (password.equals("") || password.isEmpty()) {
            throw new IllegalArgumentException("Invalid username!");
        }

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        return DriverManager.getConnection(DB_URL + DB_NAME, props);
    }
}

