package elldimi.jpacodefirstlab;

import elldimi.jpacodefirstlab.entities.Car;
import elldimi.jpacodefirstlab.entities.Plane;
import elldimi.jpacodefirstlab.entities.PlateNumber;
import elldimi.jpacodefirstlab.entities.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class JpaCodeFirstApp {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("vehicles");
        EntityManager em = factory.createEntityManager();


        Car car1 = new Car("Audi A8", new BigDecimal(56000), "hybrid", 5);
        Truck truck1 = new Truck("Fuso Canter", new BigDecimal(120000), "gasoline", 5.5);
        Plane plane1 = new Plane("Boing", new BigDecimal(1200000), "kerosene", 120);

        em.getTransaction().begin();
        em.persist(car1);

        PlateNumber number = new PlateNumber("CB1212VB", car1);
        car1.setPlateNumber(number);
        em.persist(number);

        em.persist(truck1);
        em.persist(plane1);
        em.getTransaction().commit();

    }
}
