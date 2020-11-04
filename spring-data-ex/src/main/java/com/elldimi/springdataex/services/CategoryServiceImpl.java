package com.elldimi.springdataex.services;

import com.elldimi.springdataex.entities.Category;
import com.elldimi.springdataex.repositories.CategoryRepository;
import com.elldimi.springdataex.utills.FileUtil;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static com.elldimi.springdataex.constants.GlobalConstants.CATEGORIES_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;
    private final FileUtil fileUtil;

    public CategoryServiceImpl(CategoryRepository repo, FileUtil fileUtil) {
        this.repo = repo;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategory() throws FileNotFoundException {
        String[] content = this.fileUtil.readFileContent(CATEGORIES_FILE_PATH);

        //TODO refactoring!!!
        if (this.repo.count() != 0) {
            return;
        }
        Arrays.stream(content).forEach(categoryName -> {
            Category category = new Category(categoryName);
            this.repo.saveAndFlush(category);
        });
    }

    @Override
    public Category getById(Long id) {
        return this.repo.getOne(id);
    }
}
