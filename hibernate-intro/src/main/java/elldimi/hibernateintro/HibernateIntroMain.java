package elldimi.hibernateintro;

import elldimi.hibernateintro.entities.Student;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        System.out.println("5/*n----------------------------------------------------------------------------------------------*/");

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
        System.out.println("6/*n----------------------------------------------------------------------------------------------*/");

        //6.List all students using HQL:
        session.beginTransaction();
        session.createQuery("FROM Student", Student.class)
                .setFirstResult(5)
                .setMaxResults(10)
                .stream().forEach(System.out::println);
        session.getTransaction().commit();
        System.out.println("7/*n----------------------------------------------------------------------------------------------*/");

        //7.List student with certain name:
        session.beginTransaction();
        session.createQuery("FROM Student WHERE name = :name", Student.class)
                .setParameter("name", "John Doe")
                .stream().forEach(System.out::println);
        session.getTransaction().commit();

        System.out.println();

        session.beginTransaction();
        session.createQuery("FROM Student WHERE name = ?1", Student.class)
                .setParameter(1, "John Doe")
                .stream().forEach(System.out::println);
        session.getTransaction().commit();
        System.out.println("8/*n----------------------------------------------------------------------------------------------*/");

        //8.Type-safe criteria queries:
        /*For better safety is recommended the usage of Metamodel API -
        property naming instead of using the string name of the field itself - Student_.*/
        session.beginTransaction();
        CriteriaBuilder criteria = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = criteria.createQuery(Student.class);
        Root<Student> Student_ = query.from(Student.class);
        query.select(Student_).where(criteria.like(Student_.get("name"), "D%"));
        session.createQuery(query).getResultList().forEach(System.out::println);

        System.out.println("/*n----------------------------------------------------------------------------------------------*/");
        //CLOSE SESSION!!!
        session.close();
    }
}
