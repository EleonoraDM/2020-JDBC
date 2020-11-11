package spring.elldimi.bookshopadvquerying.services;

import org.springframework.stereotype.Service;
import spring.elldimi.bookshopadvquerying.entities.*;
import spring.elldimi.bookshopadvquerying.repositories.BookRepository;
import spring.elldimi.bookshopadvquerying.utills.FileUtil;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static spring.elldimi.bookshopadvquerying.constants.GlobalConstants.BOOKS_FILE_PATH;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository repo, FileUtil fileUtil, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = repo;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws FileNotFoundException {

        //TODO refactoring!!!
        if (this.bookRepository.count() != 0) {
            return;
        }
        String[] content = this.fileUtil.readFileContent(BOOKS_FILE_PATH);


        for (String line : content) {


            Matcher matcher = Pattern
                    .compile("(^[0-9])\\s([0-9]+/[0-9]+/[0-9]{4})\\s([0-9]+)\\s([0-9]+.?[0-9]+?)\\s([0-9])\\s(.+)")
                    .matcher(line);
            while (matcher.find()) {
                Author author = this.getRandomAuthor();

                EditionType edition = EditionType.values()[Integer.parseInt(matcher.group(1))];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate releaseDate = LocalDate.parse(matcher.group(2), formatter);

                int copies = Integer.parseInt(matcher.group(3));
                BigDecimal price = new BigDecimal(matcher.group(4));

                AgeRestriction restriction = AgeRestriction.values()[Integer.parseInt(matcher.group(5))];

                String title = matcher.group(6);

                Set<Category> categories = getRandomCategory();

                Book book = new Book(author, edition, releaseDate, copies, price, restriction, title, categories);

                this.bookRepository.saveAndFlush(book);
            }
        }
    }

    @Override
    public List<Book> bookSelectionByYearsCriteria() {
        LocalDate releaseDate = LocalDate.of(2000, 12, 31);
        return this.bookRepository.findAllByReleaseDateAfter(releaseDate);
    }

    @Override
    public void printAllBooksByAgeRestriction(String ageRestr) {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(ageRestr.toUpperCase());
        this.bookRepository
                .findAllByAgeRestrictionOrderByTitle(ageRestriction)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printAllBooksByEditionAndCopies(String editionType, int numberOfCopies) {
        EditionType type = EditionType.valueOf(editionType);
        this.bookRepository
                .findAllByEditionAndCopiesLessThanEqual(type, numberOfCopies)
                .forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printBooksByBottomAndTopPrice(int bottom, int top) {
        BigDecimal bottomPrice = new BigDecimal(bottom);
        BigDecimal topPrice = new BigDecimal(top);

        this.bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(bottomPrice, topPrice)
                .forEach(b -> System.out.println(b.getTitle() + " - $" + b.getPrice()));
    }

    @Override
    public void printBooksReleasedNotInYear(String year) {
        this.bookRepository
                .findAllByReleaseDateNotInYear(year).forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printBooksReleasedBeforeDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate releaseDate = LocalDate.parse(date, formatter);

        this.bookRepository
                .findAllByReleaseDateBefore(releaseDate)
                .forEach(b -> System.out.printf("|%-30.30s |%-10.10s | %5.2f |%n",
                        b.getTitle(), b.getEdition(), b.getPrice()));
    }

    @Override
    public void printBooksWhichTitleContains(String substr) {
        this.bookRepository
                .findAllByTitleContains(substr)
                .forEach(b -> System.out.println(b.getTitle()));
    }

    @Override
    public void printBooksWhichAuthorsLastNameStartsWith(String str) {
        this.bookRepository
                .findBooksWhichAuthorsLastNameStartsWith(str)
                .forEach(b -> System.out.println(b.getTitle() + " ("
                        + b.getAuthor().getFirstName() + " "
                        + b.getAuthor().getLastName() + ")"));
    }

    @Override
    public void printCountOfBooksWithTitleLongerThan(int length) {
        int count = this.bookRepository.findBooksWithTitleLongerThan(length);
        System.out.printf("There are %d books with longer title than %d symbols%n", count, length);
    }

    @Override
    public void printBookInfoByGivenTitle(String title) {
        Book book = this.bookRepository.findBookByTitle(title);
        System.out.printf("|%-30.30s |%-7.7s|%-7.7s| %5.2f |%n",
                title,
                book.getEdition(),
                book.getAgeRestriction(),
                book.getPrice()
        );
    }


    private Set<Category> getRandomCategory() {
        Set<Category> result = new HashSet<>();
        Random random = new Random();
        int bound = random.nextInt(3) + 1;

        for (int i = 1; i <= bound; i++) {
            int categoryId = random.nextInt(8) + 1;
            result.add(this.categoryService.getById((long) categoryId));
        }
        return result;
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        //There should be Upper boundary by random generation and it is always EXCLUSIVE!!!
        //So if we have 30 authors, we should put random + 1, having in mind that SQL does not count from 0!!!
        int randomId = random.nextInt((int) this.authorService.getAuthorsCount()) + 1;
        return this.authorService.findById(((long) randomId));
    }
}
