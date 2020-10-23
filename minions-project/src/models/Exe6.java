package models;

import common.ReusableQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Exe6 extends ExerciseImpl {
    private final String TASK_NAME = "6.*Remove Villain";
    private final String DESCRIPTION = "Write a program that receives an ID of a villain, deletes him from the database and releases his minions from serving him. As an output print the name of the villain and the number of minions released. Make sure all operations go as planned, otherwise do not make any changes to the database. For the output use the format given in the examples.";

    private PreparedStatement stmt;
    private CallableStatement call;

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
        System.out.println("Enter villain id: ");

        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        int villainId = Integer.parseInt(bfr.readLine());

        String villainName = ReusableQ.getEntityNameById(super.accessConnection(), villainId, "villains");

        if (villainName != null) {

            createCountMinionsByVillainFunction();
            int retiredMinions = callCountMinionsByVillainFunction(villainId);

            createRetirementProcedure();
            callRetirementProcedure(villainId);

            System.out.printf("%s was deleted%n%d minions released%n",
                    villainName, retiredMinions);
        } else {
            System.out.println("No such villain was found");
        }
    }

    private int callCountMinionsByVillainFunction(int id) throws SQLException {
        call = super.accessConnection().prepareCall("SELECT udf_getMinionsCount(?);");
        call.setInt(1, id);
        ResultSet resultSet = call.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    private void createCountMinionsByVillainFunction() throws SQLException {
        String query = "CREATE FUNCTION `udf_getMinionsCount`(villain INT)\n" +
                "    RETURNS INT DETERMINISTIC\n" +
                "BEGIN\n" +
                "    DECLARE `retiredMinions` INT;\n" +
                "    SET retiredminions := (SELECT count(minion_id)\n" +
                "                           FROM minions_villains\n" +
                "                           WHERE villain_id = villain);\n" +
                "    RETURN retiredminions;\n" +
                "END;";

        stmt = super.accessConnection().prepareStatement(query);
        stmt.execute();
    }

    private void callRetirementProcedure(int villainId) throws SQLException {
        String callStr = "CALL usp_deleteVillain(?)";

        CallableStatement call = super.accessConnection().prepareCall(callStr);
        call.setInt(1, villainId);
        call.execute();
        call.close();
    }

    private void createRetirementProcedure() throws SQLException {
        String query = "CREATE PROCEDURE `usp_deleteVillain`(IN retiredvillain INT)\n" +
                "BEGIN\n" +
                "    DELETE FROM minions_villains WHERE villain_id = retiredvillain;\n" +
                "\n" +
                "    DELETE\n" +
                "    FROM villains\n" +
                "    WHERE id = retiredvillain;\n" +
                "END;\n";

        stmt = super.accessConnection().prepareStatement(query);
        stmt.execute();
    }
}
