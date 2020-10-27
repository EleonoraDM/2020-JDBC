import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class MiniOrmProject {
    public static void main(String[] args) throws IllegalAccessException {

        EntityManager<User> manager = null;
        try {
            Connector.setUpConnection
                    ("?????", "?????", "fsd");
            System.out.println("Connected successfully!");
            manager = new EntityManager<>
                    (Connector.accessConnection());
        } catch (SQLException ex) {
            System.out.println("Missing credentials!!!");
            ex.printStackTrace();
        }

        try {
            /*User test = new User("OO7", "007", 22, LocalDate.now());
            manager.persist(test);*/

/*            User test1 = new User();
            test1.setId(1);
            test1.setUsername("MED");
            test1.setPassword("!!!");
            test1.setAge(27);
            test1.setRegistrationDate(LocalDate.now());
            manager.persist(test1);*/

     /*       assert manager != null;
            manager.delete(User.class, 4);*/

            assert manager != null;
            System.out.println(manager.findById(User.class, 1));
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            System.out.println("Unsuccessful data persistence!");
            e.printStackTrace();
        }
    }
}
