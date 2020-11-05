package com.elldimi.springdataex.services;

import com.elldimi.springdataex.entities.*;
import com.elldimi.springdataex.repositories.BookRepository;
import com.elldimi.springdataex.utills.FileUtil;
import org.springframework.stereotype.Service;

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

import static com.elldimi.springdataex.constants.GlobalConstants.BOOKS_FILE_PATH;

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
        //So if we have 30 authors, we should put random + 1, having in mind that SQL does not count from )!!!
        int randomId = random.nextInt((int) this.authorService.getAuthorsCount()) + 1;
        return this.authorService.findById(((long) randomId));
    }
}
