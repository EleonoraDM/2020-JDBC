import common.DatabaseConnection;
import engine.HomeworkImpl;

import java.io.IOException;
import java.sql.SQLException;

public class MinionsApp {

    public static void main(String[] args) throws SQLException, IOException {

        HomeworkImpl homework = new HomeworkImpl
                (DatabaseConnection.setUpConnection("????", "????"));
        homework.executeTask();
    }
}
