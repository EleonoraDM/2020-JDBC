package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exe9 extends ExerciseImpl {
    private final String TASK_NAME = "9.Increase Age Stored Procedure";
    private final String DESCRIPTION =
            "Read from the console minion IDs, separated by space. Increment the age of those minions by 1 and make their names title to lower case. Finally, print the names and the ages of all minions that are in the database. See the examples below.";

    private PreparedStatement stmt;
    private ResultSet resultSet;


    public Exe9(Connection connection) {
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

        System.out.println("Already done at Exe8!");
    }
}
