import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        //DELETE PASSWORD FROM META-INF/persistence.xml!!!

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("soft_uni");

        EntityManager manager = emf.createEntityManager();
        Engine engine = new Engine(manager);
        engine.run();

    }
}
