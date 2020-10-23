package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exe6 extends ExerciseImpl {
    private final String TASK_NAME = "6.*Remove Villain";
    private final String DESCRIPTION = "Write a program that receives an ID of a villain, deletes him from the database and releases his minions from serving him. As an output print the name of the villain and the number of minions released. Make sure all operations go as planned, otherwise do not make any changes to the database. For the output use the format given in the examples.";

    private PreparedStatement stmt;
    private ResultSet resultSet;


    public Exe6(Connection connection) {
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

        System.out.println("Not enough time :)");

    }
}
