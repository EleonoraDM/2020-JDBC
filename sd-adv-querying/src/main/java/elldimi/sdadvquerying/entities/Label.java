package elldimi.sdadvquerying.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "labels")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String subtitle;

    @OneToMany(mappedBy = "label")
    private Set<Shampoo> shampoos;



}