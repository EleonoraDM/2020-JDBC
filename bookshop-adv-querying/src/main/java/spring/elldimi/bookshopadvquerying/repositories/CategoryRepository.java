package spring.elldimi.bookshopadvquerying.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.elldimi.bookshopadvquerying.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
