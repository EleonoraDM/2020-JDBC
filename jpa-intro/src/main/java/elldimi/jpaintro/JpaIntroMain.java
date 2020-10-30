package elldimi.jpaintro;

import elldimi.jpaintro.entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JpaIntroMain {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("school_jpa");

        EntityManager em = factory.createEntityManager();

        Student student = new Student("John Doe");
        Student student1 = new Student("Grigor Dimitrov");
        Student student2 = new Student("Tsvetanka Rizova");
        Student student3 = new Student("Rumen Radev");
        Student student4 = new Student("Boko Tikvata");

        //1.Persist object via JPA - INSERT new!
        em.getTransaction().begin();
        em.persist(student);
        em.persist(student1);
        em.persist(student2);
        em.persist(student3);
        em.persist(student4);
        em.getTransaction().commit();

        //2.Find Object via JPA by ID!
        em.getTransaction().begin();
        Student found = em.find(Student.class, 1L);
        System.out.printf("Found student: %s%n", student.toString());
        em.getTransaction().commit();

        //2.Find object via JPA by ID!
        em.getTransaction().begin();
        List resultList = em.createQuery
                ("SELECT s from Student as s where s.name like :name order by s.name desc ")
                .setParameter("name", "%")
                .getResultList();
        for (Object s : resultList) {
            System.out.println(s.toString());
        }
        em.getTransaction().commit();

/*        //3.Remove object via JPA!
        em.getTransaction().begin();
        System.out.printf("Removed entity: %s%n", em.find(Student.class, 1L).toString());
        em.remove(found);
        em.getTransaction().commit();*/

        //4.Merge object via JPA!
        em.getTransaction().begin();
        em.detach(student1);
        student1.setName("Kiflio Dimitrov");
        Student merged = em.merge(student1);
        System.out.printf("Same reference: %b", merged == student1);
        em.getTransaction().commit();


        em.close();

    }
}
