package elldimi.sdadvquerying.dao;

import elldimi.sdadvquerying.entities.Label;
import elldimi.sdadvquerying.entities.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
}
