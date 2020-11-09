package elldimi.sdadvquerying.dao;

import elldimi.sdadvquerying.entities.Shampoo;
import elldimi.sdadvquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findBySizeOrderById(Size size);
//    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size size);

}
