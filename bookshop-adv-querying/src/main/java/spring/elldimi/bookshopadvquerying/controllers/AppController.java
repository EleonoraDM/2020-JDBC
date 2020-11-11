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
        System.out.println("6--------------------------------------------------------");
        this.authorService.printAuthorsWithFirstNameEndsWith("e");

        //7.	Books Search
        System.out.println("7--------------------------------------------------------");
        this.bookService.printBooksWhichTitleContains("sK");

        //8.	Book Titles Search
        System.out.println("8--------------------------------------------------------");
        this.bookService.printBooksWhichAuthorsLastNameStartsWith("Ric%");

        //9.	Count Books
        System.out.println("9--------------------------------------------------------");
        this.bookService.printCountOfBooksWithTitleLongerThan(40);

        //10.	Total Book Copies
        System.out.println("10--------------------------------------------------------");
        this.authorService.listAndOrderAllAuthorsByTotalBookCopies();

        //11.	Reduced Book
        System.out.println("11--------------------------------------------------------");
        this.bookService.printBookInfoByGivenTitle("Things Fall Apart");

        //12.	* Increase Book Copies
        /*Write a program that increases the copies of all books
          released after a given date
          with a given number.
          Print the total amount of book copies that were added.
         Input
          •	On the first line – date in the format dd MMM yyyy.
            If a book is released after that date (exclusively),
            increase its book copies with the provided number from the second line of the input.
          •	On the second line – number of book copies each book should be increased with.
         Output
          •	Total number of books that was added to the database.
*/
        System.out.println("12--------------------------------------------------------");
        this.bookService.increaseBookCopies("01-01-2014", 100);

        //13.	* Remove Books
        /*Write a program that removes from the database those books, which copies are lower than a given number. Print the number of books that were deleted from the database.*/

        //14.	* Stored Procedure
        /*14.	* Stored Procedure
Using Workbench (or other similar tool) create a stored procedure, which receives an author's first and last name and returns the total amount of books the author has written. Then write a program that receives an author's name and prints the total number of books the author has written by using the stored procedure you've just created.
*/
    }


}
