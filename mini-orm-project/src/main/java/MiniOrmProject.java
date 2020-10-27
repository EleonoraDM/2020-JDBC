import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.sql.SQLException;
import java.time.LocalDate;

public class MiniOrmProject {
    public static void main(String[] args) throws IllegalAccessException {

        try {
            Connector.setUpConnection
                    ("?????", "?????", "fsd");
            System.out.println("Connected successfully!");

            EntityManager<User> manager = new EntityManager<>
                    (Connector.accessConnection());

            User test = new User("OO7", "007", 22, LocalDate.now());
            manager.persist(test);

/*            User test1 = new User();
            test1.setId(1);
            test1.setUsername("MED");
            test1.setPassword("!!!");
            test1.setAge(27);
            test1.setRegistrationDate(LocalDate.now());
            manager.persist(test1);*/

        } catch (SQLException e) {
            System.out.println("Missing credentials!!!");
            e.getStackTrace();
        }
    }
}
