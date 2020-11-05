package com.elldimi.springdataex.services;

import com.elldimi.springdataex.entities.Author;
import com.elldimi.springdataex.repositories.AuthorRepository;
import com.elldimi.springdataex.utills.FileUtil;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static com.elldimi.springdataex.constants.GlobalConstants.AUTHOR_FILE_PATH;

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
}
