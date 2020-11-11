package spring.elldimi.bookshopadvquerying.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.elldimi.bookshopadvquerying.entities.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author AS a ORDER BY a.books.size DESC")
    List<Author> findAuthorByCountOfBooks();

/*    @Query()
    List<Author> findAuthorByBookReleaseDate();*/

    List<Author> findAllByFirstNameEndsWith(String letter);

    List<Author> findAuthorsBy();


}
