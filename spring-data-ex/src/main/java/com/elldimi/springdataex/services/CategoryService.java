package com.elldimi.springdataex.services;

import com.elldimi.springdataex.entities.Category;

import java.io.FileNotFoundException;

public interface CategoryService {
    void seedCategory() throws FileNotFoundException;

    Category getById(Long id);
}
