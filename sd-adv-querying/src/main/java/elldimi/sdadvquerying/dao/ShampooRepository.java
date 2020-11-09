package elldimi.sdadvquerying.dao;

import elldimi.sdadvquerying.entities.Shampoo;
import elldimi.sdadvquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findBySizeOrderById(Size size);

    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size size, Long label_id);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(double price);

    List<Shampoo> findByPriceLessThan(double price);

    @Query("SELECT s FROM Shampoo AS s JOIN s.ingredients AS i WHERE i.name IN :ingredient_names")
    List<Shampoo> findWithIngredientsIn(@Param("ingredient_names") String...ingredientNames);

    @Query("SELECT s FROM Shampoo AS s WHERE s.ingredients.size < :number")
    List<Shampoo> findAllByIngredientsCount(@Param("number") int number);


}
