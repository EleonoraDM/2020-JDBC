package models;

import common.ReusableQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exe3 extends ExerciseImpl {
    private final String TASK_NAME = "3.Get Minion Names";
    private final String DESCRIPTION = "Write a program that prints on the console all minion names and their age for given villain id. For the output, use the formats given in the examples.";

    public Exe3(Connection connection) {
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

        System.out.println("Enter villain id: ");
        int villainId = Integer.parseInt(reader.readLine());

        String villainName = ReusableQ.getEntityNameById
                (super.accessConnection(), villainId, "villains");

        if (villainName == null) {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
        } else {
            System.out.printf("Villain: %s%n", villainName);
        }

        String query =
                "SELECT m.name, m.age\n" +
                        "FROM minions_db.minions m\n" +
                        "         JOIN minions_db.minions_villains mv ON m.id = mv.minion_id\n" +
                        "WHERE mv.villain_id = ?";

        PreparedStatement stmt = super.accessConnection().prepareStatement(query);
        stmt.setInt(1, villainId);
        ResultSet resultSet = stmt.executeQuery();

        int counter = 0;

        while (resultSet.next()) {

            System.out.printf("%d. %s %d%n",
                    ++counter,
                    resultSet.getString("name"),
                    resultSet.getInt("age"));
        }
    }
}
