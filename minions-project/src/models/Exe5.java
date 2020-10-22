package models;

import common.ReusableQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Exe5 extends ExerciseImpl {
    private final String TASK_NAME = "5.Change Town Names Casing";
    private final String DESCRIPTION = "Write a program that changes all town names to uppercase for a given country. Print the number of towns that were changed in the format provided in examples. On the next line print the names that were changed, separated by coma and a space.";


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

        int result = checkIfCountryExists(country);
        if (result < 0) {
            throw new IllegalArgumentException("Country with the given name does not exist!");
        }

        String query =
                "UPDATE towns SET towns.name = UPPER(towns.name) WHERE country = ?";
        PreparedStatement stmt = super.accessConnection().prepareStatement(query);
        stmt.setString(1, country);
        int count = stmt.executeUpdate();

        System.out.printf("%d town names were affected.%n",count);
                //"[SOFIA, PLOVDIV, BURGAS]%n"

    }

    private int checkIfCountryExists(String country) throws SQLException {
        return ReusableQ.getEntityIdByName(super.accessConnection(), country, "towns");
    }
}
