package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exe2 extends ExerciseImpl {
    private final String TASK_NAME = "2.Get Villains’ Names";
    private final String DESCRIPTION = "Write a program that prints on the console all villains’ names and their number of minions. Get only the villains who have more than 15 minions. Order them by number of minions in descending order.";

    private PreparedStatement stmt;
    private ResultSet resultSet;

    public Exe2(Connection connection) {
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
    public void execute() throws SQLException {
        stmt = super.accessConnection().prepareStatement(
                "SELECT v.name, COUNT(mv.minion_id) AS `count`\n" +
                        "FROM minions_db.villains AS v\n" +
                        "JOIN minions_db.minions_villains mv ON v.id = mv.villain_id\n" +
                        "GROUP BY v.name\n" +
                        "HAVING count > 15\n" +
                        "ORDER BY count DESC");

        resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d\n",
                    resultSet.getString("name"),
                    resultSet.getInt("count"));
        }
    }
}
