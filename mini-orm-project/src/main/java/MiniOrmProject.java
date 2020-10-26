import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.sql.SQLException;
import java.time.LocalDate;

public class MiniOrmProject {
    public static void main(String[] args) throws SQLException, IllegalAccessException {

        Connector.setUpConnection
                ("?????", "?????", "fsd");

        EntityManager<User> manager = new EntityManager<>
                (Connector.accessConnection());

        System.out.println("Connected successfully!");

        User test = new User("MED", "MED", 26, LocalDate.now());
        manager.persist(test);
    }
}
