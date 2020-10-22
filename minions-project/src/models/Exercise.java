package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface Exercise {

    Connection accessConnection();
    String getName();
    String getDescription();
    void execute() throws SQLException, IOException;
}
