package spring.elldimi.bookshopadvquerying.services;

import org.springframework.stereotype.Service;
import spring.elldimi.bookshopadvquerying.entities.Author;
import spring.elldimi.bookshopadvquerying.entities.Book;
import spring.elldimi.bookshopadvquerying.repositories.AuthorRepository;
import spring.elldimi.bookshopadvquerying.utills.FileUtil;

import java.io.FileNotFoundException;
import java.util.*;

import static spring.elldimi.bookshopadvquerying.constants.GlobalConstants.AUTHOR_FILE_PATH;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws FileNotFoundException {
        //TODO refactoring!!!
        if (this.authorRepository.count() != 0) {
            return;
        }
        String[] content = this.fileUtil.readFileContent(AUTHOR_FILE_PATH);

        Arrays.stream(content).forEach(authorFullName -> {
            String[] fullName = authorFullName.split("\\s+");
            String firstName = fullName[0];
            String lastName = fullName[1];
            Author author = new Author(firstName, lastName);
            this.authorRepository.saveAndFlush(author);
        });
    }

    @Override
    public long getAuthorsCount() {
        return this.authorRepository.count();
    }

    @Override
    public Author findById(Long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> findAllAuthorsByBooksCount() {
        return this.authorRepository.findAuthorByCountOfBooks();
    }

    @Override
    public void printAuthorsWithFirstNameEndsWith(String letter) {
        this.authorRepository
                .findAllByFirstNameEndsWith(letter)
                .forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    @Override
    public void listAndOrderAllAuthorsByTotalBookCopies() {
        List<Author> authors = this.authorRepository.findAuthorsBy();
        HashMap<String, Integer> authorsAndCopies = new HashMap<>();

        for (Author author : authors) {
            String fullName = author.getFirstName() + " " + author.getLastName();
            int copies = author.getBooks().stream().mapToInt(Book::getCopies).sum();
            authorsAndCopies.put(fullName, copies);
        }
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

        authorsAndCopies.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        reverseSortedMap.entrySet()
                .forEach(entry-> System.out.printf("%s - %d%n", entry.getKey(), entry.getValue()));
    }

}
