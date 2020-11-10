package spring.elldimi.bookshopadvquerying.services;

import spring.elldimi.bookshopadvquerying.entities.Category;

import java.io.FileNotFoundException;

public interface CategoryService {
    void seedCategory() throws FileNotFoundException;

    Category getById(Long id);
}
