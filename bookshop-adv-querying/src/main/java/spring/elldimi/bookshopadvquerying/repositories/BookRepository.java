package spring.elldimi.bookshopadvquerying.repositories;

import org.hibernate.annotations.Where;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.elldimi.bookshopadvquerying.entities.AgeRestriction;
import spring.elldimi.bookshopadvquerying.entities.Book;
import spring.elldimi.bookshopadvquerying.entities.EditionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    List<Book> findAllByAgeRestrictionOrderByTitle(AgeRestriction ageRestriction);

    List<Book> findAllByEditionAndCopiesLessThanEqual(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal bottomPrice, BigDecimal topPrice);

    @Query("Select b from Book as b Where substring(b.releaseDate,0,4) NOT LIKE :year")
    List<Book> findAllByReleaseDateNotInYear(@Param("year") String year);

    List<Book> findAllByReleaseDateBefore(LocalDate date);
}
