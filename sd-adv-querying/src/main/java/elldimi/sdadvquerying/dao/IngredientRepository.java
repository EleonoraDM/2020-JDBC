package elldimi.sdadvquerying.dao;

import elldimi.sdadvquerying.entities.Ingredient;
import elldimi.sdadvquerying.entities.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
