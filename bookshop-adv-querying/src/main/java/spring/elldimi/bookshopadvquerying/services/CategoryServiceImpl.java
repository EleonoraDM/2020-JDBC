package spring.elldimi.bookshopadvquerying.services;

import org.springframework.stereotype.Service;
import spring.elldimi.bookshopadvquerying.entities.Category;
import spring.elldimi.bookshopadvquerying.repositories.CategoryRepository;
import spring.elldimi.bookshopadvquerying.utills.FileUtil;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static spring.elldimi.bookshopadvquerying.constants.GlobalConstants.CATEGORIES_FILE_PATH;

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
