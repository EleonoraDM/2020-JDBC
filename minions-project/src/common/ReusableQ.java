package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReusableQ {
    private static PreparedStatement stmt;


    public static String getEntityNameById
            (Connection conn, int entityId, String tableName)
            throws SQLException {

        String query = String.format("SELECT name FROM %s WHERE id = ?", tableName);

        stmt = conn.prepareStatement(query);
        stmt.setInt(1, entityId);

        ResultSet resultSet = stmt.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;
    }

    public static String getEntityByName
            (Connection conn, String name, String tableName)
            throws SQLException {

        String query = String.format("SELECT name FROM %s WHERE name = ?", tableName);

        stmt = conn.prepareStatement(query);
        stmt.setString(1, name);

        ResultSet resultSet = stmt.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;
    }

    public static int getEntityIdByName(Connection conn, String entityName, String tableName) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = ?", tableName);

        stmt = conn.prepareStatement(query);
        stmt.setString(1, entityName);

        ResultSet resultSet = stmt.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

}
