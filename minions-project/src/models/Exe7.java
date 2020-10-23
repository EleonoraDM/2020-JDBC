package models;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Exe7 extends ExerciseImpl {
    private final String TASK_NAME = "7.Print All Minion Names";
    private final String DESCRIPTION = "Write a program that prints all minion names from the minions table in order first record, last record, first + 1, last – 1, first + 2, last – 2… first + n, last – n.";

    private PreparedStatement stmt;
    private ResultSet resultSet;


    public Exe7(Connection connection) {
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
        String query = "SELECT name FROM minions";

        stmt = super.accessConnection().prepareStatement(query);
        resultSet = stmt.executeQuery();

        ArrayDeque<String> names = new ArrayDeque<>();

        while (resultSet.next()) {
            names.add(resultSet.getString("name"));
        }
        System.out.println();

        while (!names.isEmpty()){
            System.out.println(names.removeFirst());
            System.out.println(names.removeLast());
        }
    }
}
