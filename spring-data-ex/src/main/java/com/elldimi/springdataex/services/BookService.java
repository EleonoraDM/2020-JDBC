package com.elldimi.springdataex.services;

import com.elldimi.springdataex.entities.Book;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public interface BookService {
    void seedBooks() throws FileNotFoundException, ParseException;

    List<Book> bookSelectionByYearsCriteria();
}
