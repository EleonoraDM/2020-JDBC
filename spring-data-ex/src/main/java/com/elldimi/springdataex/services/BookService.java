package com.elldimi.springdataex.services;

import java.io.FileNotFoundException;
import java.text.ParseException;

public interface BookService {
    void seedBooks() throws FileNotFoundException, ParseException;
}
