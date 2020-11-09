package elldimi.sdadvquerying.dao;

import elldimi.sdadvquerying.entities.Ingredient;
import elldimi.sdadvquerying.entities.Shampoo;
import org.hibernate.sql.Delete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameStartingWith(String beginningLetter);

    List<Ingredient> findByNameIn(String ... ingredientNames);

    @Transactional
    int deleteAllByName(String wild_rose);

    Optional<Ingredient> findByName(String ingName);
}

