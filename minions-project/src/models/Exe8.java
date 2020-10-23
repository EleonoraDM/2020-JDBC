package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;

public class Exe8 extends ExerciseImpl {
    private final String TASK_NAME = "8.Increase Minions Age";
    private final String DESCRIPTION =
            "Read from the console minion IDs, separated by space. Increment the age of those minions by 1 and make their names title to lower case. Finally, print the names and the ages of all minions that are in the database. See the examples below.";

    private PreparedStatement stmt;
    private ResultSet resultSet;

    public Exe8(Connection connection) {
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
        System.out.println("Enter minions ids: ");
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));

        int[] ids = Arrays.stream(bfr.readLine()
                .split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        createIncreasesAgeProcedure();
        increaseMinionsAge(ids);
        printAllMinions();
    }

    private void createIncreasesAgeProcedure() throws SQLException {

        String query = "CREATE PROCEDURE `udp_increase_age`(IN idinp INT)\n" +
                "BEGIN\n" +
                "    UPDATE minions AS `m`\n" +
                "    SET m.age  = m.age + 1,\n" +
                "        m.name = LOWER(m.name)\n" +
                "    WHERE m.id = idinp;\n" +
                "END;";
        stmt = super.accessConnection().prepareStatement(query);
        stmt.execute();
    }

    private void printAllMinions() throws SQLException {

        String query = "SELECT name, age FROM minions";
        stmt = super.accessConnection().prepareStatement(query);
        resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n",
                    resultSet.getString("name"),
                    resultSet.getInt("age"));
        }
    }

    private void increaseMinionsAge(int[] ids) throws SQLException {
        for (int id : ids) {
            String query = "CALL minions_db.udp_increase_age(?)";
            CallableStatement callableStatement = super.accessConnection().prepareCall(query);
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        }
    }
}
