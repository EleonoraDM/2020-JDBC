import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;

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
            assert manager != null;

            User test = new User("SKE", "SKE", 46, LocalDate.now());

            manager.persist(test);

            manager.delete(User.class, 6);

            manager.printObjectData(manager.findById(User.class, 1));

            manager.printMultipleObjectsData
                    (manager.find(User.class,
                            "YEAR(registration_date) > ? age > ?",
                            2010, 18));

        } catch (SQLException e) {
            System.out.println("Unsuccessful data persistence!");
            e.printStackTrace();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
