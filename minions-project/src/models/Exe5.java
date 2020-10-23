package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Exe5 extends ExerciseImpl {
    private final String TASK_NAME = "5.Change Town Names Casing";
    private final String DESCRIPTION = "Write a program that changes all town names to uppercase for a given country. Print the number of towns that were changed in the format provided in examples. On the next line print the names that were changed, separated by coma and a space.";

    private PreparedStatement stmt;
    private ResultSet resultSet;


    public Exe5(Connection connection) {
        super(connection);
    }

    @Override
    public String getName() {
        return TASK_NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void execute() throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter country: ");
        String country = reader.readLine();

        int result = checkIfCountryExists(super.accessConnection(), country);
        if (result < 0) {
            System.out.println("No town names were affected.");
        }else {
            int count = getUpdatedFieldsCount(country);
            System.out.printf("%d town names were affected.%n", count);

            printUpdatedTowns(country);
        }
    }

    private void printUpdatedTowns(String country) throws SQLException {
        StringBuilder builder = new StringBuilder();

        String query = "SELECT name FROM towns WHERE country = ?";
        stmt = super.accessConnection().prepareStatement(query);
        stmt.setString(1, country);

        resultSet = stmt.executeQuery();

        builder.append("[");
        Set<String> updatedTowns = new HashSet<>();

        while (resultSet.next()) {
            updatedTowns.add(resultSet.getString("name"));
        }
        builder.append(updatedTowns.stream().collect(Collectors.joining(", ")));
        builder.append("]");
        System.out.println(builder.toString());
    }

    private int getUpdatedFieldsCount(String country) throws SQLException {
        String query =
                "UPDATE towns SET towns.name = UPPER(towns.name) WHERE country = ?";
        stmt = super.accessConnection().prepareStatement(query);
        stmt.setString(1, country);
        return stmt.executeUpdate();
    }

    private int checkIfCountryExists(Connection conn, String country) throws SQLException {

        String query = "SELECT id FROM towns WHERE country = ?";

        stmt = conn.prepareStatement(query);
        stmt.setString(1, country);

        resultSet = stmt.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : -1;

    }
}
