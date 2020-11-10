package spring.elldimi.bookshopadvquerying.controllers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import spring.elldimi.bookshopadvquerying.services.AuthorServiceImpl;
import spring.elldimi.bookshopadvquerying.services.BookServiceImpl;
import spring.elldimi.bookshopadvquerying.services.CategoryServiceImpl;


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

/*        //seed data
        this.categoryService.seedCategory();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();*/

/*        //query1
        //List<Book> books = this.bookService.bookSelectionByYearsCriteria();

        //query3
        this.authorService
                .findAllAuthorsByBooksCount()
                .forEach(a -> System.out.printf("%s %s %d%n",
                        a.getFirstName(),
                        a.getLastName(),
                        a.getBooks().size()));*/

        //1.	Books Titles by Age Restriction
        System.out.println("1--------------------------------------------------------");
        this.bookService.printAllBooksByAgeRestriction("minor");


        //2.	Golden Books
        System.out.println("2--------------------------------------------------------");
        this.bookService.printAllBooksByEditionAndCopies("GOLD", 5000);


        //3.	Books by Price
        System.out.println("3--------------------------------------------------------");
        this.bookService.printBooksByBottomAndTopPrice(5, 40);

        //4.	Not Released Books
        System.out.println("4--------------------------------------------------------");
        this.bookService.printBooksReleasedNotInYear("1998");

        //5.	Books Released Before Date
        System.out.println("5--------------------------------------------------------");
        this.bookService.printBooksReleasedBeforeDate("12-04-1992");

        //6.	Authors Search


        //7.	Books Search

        //8.	Book Titles Search

        //9.	Count Books

        //10.	Total Book Copies

        //11.	Reduced Book
    }


}
