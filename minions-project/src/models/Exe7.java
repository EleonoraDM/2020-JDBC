package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Exe7 extends ExerciseImpl {
    protected Exe7(Connection connection) {
        super(connection);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void execute() throws SQLException, IOException {

    }
}
