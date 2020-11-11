package elldimi.sdadvquerying.dao;

import elldimi.sdadvquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface        IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameStartingWith(String beginningLetter);

    List<Ingredient> findByNameIn(String... ingredientNames);

    Optional<Ingredient> findByName(String ingName);

    @Transactional
    int deleteAllByName(String name);

    @Transactional
    @Modifying
    @Query("Update Ingredient as i set i.price = i.price * :percentage where i.name IN :ingredient_names")
    int updatePriceOfIngredientsInList(@Param("percentage") double percentage,
                                       @Param("ingredient_names") String... ingredientNames);
}

