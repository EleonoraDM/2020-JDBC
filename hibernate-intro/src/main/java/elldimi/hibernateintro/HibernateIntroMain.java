package elldimi.hibernateintro;

import elldimi.hibernateintro.entities.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateIntroMain {
    public static void main(String[] args) {
        //1.Create Hibernate config
        Configuration cfg = new Configuration();
        cfg.configure();//takes the default hibernate.cfg.xml!!!

        //Create SessionFactory
        SessionFactory sf = cfg.buildSessionFactory();

        //Create Session - there is an Object Graph to manage this Session!
        Session session = sf.openSession();//sf.getCurrentSession();

        //Persist an entity - INSERT:
        Student student = new Student("Elle Macpherson");
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();

        //Close session
        //session.close();

        //Read entity by id = 1:
        session.beginTransaction();
        //1.Disable Auto-flush. Flushing is the moment of synchronization between data and disk!
        //2.LockMode.READ - the mapping class can be accessed for reading by more than one query/reader/
        //These two are optional for better performance, only by READING!!!
        session.setHibernateFlushMode(FlushMode.MANUAL);
        //Student student1 = session.get(Student.class, 1L, LockMode.READ);
        Student student2 = session.byId(Student.class).load(1L);
        session.getTransaction().commit();
        System.out.println(student2.toString());

        session.close();
    }
}
