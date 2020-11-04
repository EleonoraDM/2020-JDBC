package com.elldimi.springdataex.services;

import com.elldimi.springdataex.entities.Author;

import java.io.FileNotFoundException;

public interface AuthorService {
    void seedAuthors() throws FileNotFoundException;

    long getAuthorsCount();

    Author findById(Long id);
}
