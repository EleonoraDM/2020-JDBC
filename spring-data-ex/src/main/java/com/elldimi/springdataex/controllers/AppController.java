package com.elldimi.springdataex.controllers;

import com.elldimi.springdataex.services.AuthorServiceImpl;
import com.elldimi.springdataex.services.BookServiceImpl;
import com.elldimi.springdataex.services.CategoryServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;


@Controller
public class AppController implements CommandLineRunner {
    private final CategoryServiceImpl categoryService;
    private final AuthorServiceImpl authorService;
    private final BookServiceImpl bookService;

    public AppController(CategoryServiceImpl categoryService, AuthorServiceImpl authorService, BookServiceImpl bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategory();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }


}
