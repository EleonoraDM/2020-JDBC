package spring.elldimi.bookshopadvquerying.services;

import spring.elldimi.bookshopadvquerying.entities.Book;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public interface BookService {
    void seedBooks() throws FileNotFoundException, ParseException;

    List<Book> bookSelectionByYearsCriteria();

    void printAllBooksByAgeRestriction(String ageRestr);

    void printAllBooksByEditionAndCopies(String editionType, int numberOfCopies);

    void printBooksByBottomAndTopPrice(int bottom, int top);

    void printBooksReleasedNotInYear(String year);

    void printBooksReleasedBeforeDate(String date);

    void printBooksWhichTitleContains(String substr);

    void printBooksWhichAuthorsLastNameStartsWith(String str);

    void printCountOfBooksWithTitleLongerThan(int length);

    void printBookInfoByGivenTitle(String title);
}
