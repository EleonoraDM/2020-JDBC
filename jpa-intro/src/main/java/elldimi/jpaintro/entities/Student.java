package elldimi.jpaintro.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/*JPA:
* 1.Abstract or Concrete TOP LEVEL java class;
* 2.Annotation-based data persistence - annotated PRIVATE fields OR PUBLIC getters!!!
* 3.Non-arguments constructor */
@Entity//-> Java
@Table(name = "students")//-> DB
@Data//Lombok
@NoArgsConstructor//0 args
@RequiredArgsConstructor//name
@AllArgsConstructor//all
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "registration_date") //DB (@Basic -> Java. If object is NOT NULL.)
    private Date registrationDate = new Date();

}
