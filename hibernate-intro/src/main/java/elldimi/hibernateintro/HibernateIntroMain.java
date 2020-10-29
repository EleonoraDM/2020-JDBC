package elldimi.hibernateintro;

import elldimi.hibernateintro.entities.Student;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateIntroMain {
    public static void main(String[] args) {
        //1.Create Hibernate config
        Configuration cfg = new Configuration();
        cfg.configure();//takes the default hibernate.cfg.xml!!!

        /*n----------------------------------------------------------------------------------------------*/

        //2.Create SessionFactory
        SessionFactory sf = cfg.buildSessionFactory();

        /*n----------------------------------------------------------------------------------------------*/

        //3.Create Session - there is an Object Graph to manage this Session!
        Session session = sf.openSession();//sf.getCurrentSession();

        /*n----------------------------------------------------------------------------------------------*/
        //4.Persist an entity - INSERT:
        Student student = new Student("John Doe");
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();

        /*n----------------------------------------------------------------------------------------------*/

        //5.Read entity by id = 1:
        session.beginTransaction();
/*      1.Disable Auto-flush. Flushing is the moment of synchronization between data and disk!
        2.LockMode.READ - the mapping class can be accessed for reading by more than one query/reader/
        These two are optional for better performance, only by READING!!!*/

        session.setHibernateFlushMode(FlushMode.MANUAL);

        //Student student1 = session.get(Student.class, 1L, LockMode.READ);
        Student student2 = session.byId(Student.class).load(1L);
        session.getTransaction().commit();
        System.out.println(student2.toString());

        /*n----------------------------------------------------------------------------------------------*/
        //6.List all students using HQL:
        session.beginTransaction();
        session.createQuery("FROM Student", Student.class)
                .setFirstResult(5)
                .setMaxResults(10)
                .stream().forEach(System.out::println);
        session.getTransaction().commit();
        /*n----------------------------------------------------------------------------------------------*/

        //7.List student with certain name:
        session.beginTransaction();
        session.createQuery("FROM Student WHERE name = :name", Student.class)
                .setParameter("name", "John Doe")
                .stream().forEach(System.out::println);
        session.getTransaction().commit();

        session.beginTransaction();
        session.createQuery("FROM Student WHERE name = ?", Student.class)
                .setParameter(0, "John Doe")
                .stream().forEach(System.out::println);
        session.getTransaction().commit();
        /*n----------------------------------------------------------------------------------------------*/

        //CLOSE SESSION!!!
        session.close();
    }
}
