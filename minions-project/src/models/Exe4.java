package models;

import common.ReusableQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Exe4 extends ExerciseImpl {
    private final String TASK_NAME = "4.Add Minion";
    private final String DESCRIPTION = "Write a program that reads information about a minion and its villain and adds it to the database. In case the town of the minion is not in the database, insert it as well. In case the villain is not present in the database, add him too with default evilness factor of “evil”. Finally, set the new minion to be servant of the villain. Print appropriate messages after each operation – see the examples.";

    private PreparedStatement stmt;

    public Exe4(Connection connection) {
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

        System.out.println("Expecting information about minion and its villain: ");

        String[] minionsInfo = reader.readLine().split("\\s+");
        String[] villainsInfo = reader.readLine().split("\\s+");

        String minionsName = minionsInfo[1];
        int age = Integer.parseInt(minionsInfo[2]);
        String townName = minionsInfo[3];

        String villainsName = villainsInfo[1];

        int townId = checkIfTownExists(townName);
        int villainId = checkIfVillainExists(villainsName);
        addMinion(minionsName, age, townId, villainsName);
        setServant(minionsName, villainsName, villainId);
    }

    private int checkIfVillainExists(String name) throws SQLException {
        int villainId = ReusableQ.getEntityIdByName
                (super.accessConnection(), name, "villains");

        if (villainId < 0) {

            String query =
                    "INSERT INTO minions_db.villains(name, evilness_factor) VALUE (?,?)";
            stmt = super.accessConnection().prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, "evil");
            stmt.execute();

            System.out.printf("Villain %s was added to the database.%n", name);
        }
        return ReusableQ.getEntityIdByName(super.accessConnection(), name, "villains");
    }

    private int checkIfTownExists(String name) throws SQLException {
        int townId = ReusableQ.getEntityIdByName(super.accessConnection(), name, "towns");

        if (townId < 0) {

            String query = "INSERT INTO minions_db.towns(name) VALUE(?)";
            stmt = super.accessConnection().prepareStatement(query);
            stmt.setString(1, name);
            stmt.execute();

            System.out.printf("Town %s was added to the database.%n", name);
        }
        return ReusableQ.getEntityIdByName(super.accessConnection(), name, "towns");
    }

    private void addMinion(String minionsName, int age, int townId, String villainsName) throws SQLException {
        String table = "minions";

        String query =
                "INSERT INTO minions_db.minions (name, age, town_id) VALUE (?,?,?)";

        stmt = super.accessConnection().prepareStatement(query);
        stmt.setString(1, minionsName);
        stmt.setInt(2, age);
        stmt.setInt(3, townId);
        stmt.execute();

        System.out.printf
                ("Successfully added %s to be minion of %s.%n", minionsName, villainsName);
    }

    private void setServant(String minionName, String villainName, int villainId) throws SQLException {
        int minionsId = ReusableQ.getEntityIdByName
                (super.accessConnection(), minionName, "minions");

        String query =
                "INSERT INTO minions_db.minions_villains VALUES (?,?)";

        stmt = super.accessConnection().prepareStatement(query);
        stmt.setInt(1, minionsId);
        stmt.setInt(2, villainId);

        if (stmt.execute()) {
            System.out.printf
                    ("Successfully added %s to be minion of %s.", minionName, villainName);
        }
    }
}
