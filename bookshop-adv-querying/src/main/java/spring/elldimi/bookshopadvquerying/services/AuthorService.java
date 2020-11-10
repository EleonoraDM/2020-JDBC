package spring.elldimi.bookshopadvquerying.services;

import spring.elldimi.bookshopadvquerying.entities.Author;

import java.io.FileNotFoundException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws FileNotFoundException;

    long getAuthorsCount();

    Author findById(Long id);

    List<Author> findAllAuthorsByBooksCount();
}
