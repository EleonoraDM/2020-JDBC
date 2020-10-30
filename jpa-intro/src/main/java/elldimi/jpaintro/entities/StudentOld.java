package elldimi.jpaintro.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/*JPA:
* 1.Abstract or Concrete TOP LEVEL java class;
* 2.Annotation-based data persistence - annotated PRIVATE fields OR PUBLIC getters!!!
* 3.Non-arguments constructor */
@Entity//-> Java
@Table(name = "students")//-> DB
public class StudentOld {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "registration_date") //DB (@Basic -> Java. If object is NOT NULL.)
    private Date registrationDate = new Date();

    public StudentOld() {
    }

    public StudentOld(String name) {
        this.name = name;
    }

    public StudentOld(String name, Date registrationDate) {
        this.name = name;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentOld)) return false;
        StudentOld student = (StudentOld) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", registrationDate=").append(registrationDate);
        sb.append('}');
        return sb.toString();
    }
}
