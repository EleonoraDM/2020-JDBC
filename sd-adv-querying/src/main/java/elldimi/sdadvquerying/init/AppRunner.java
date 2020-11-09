package elldimi.sdadvquerying.init;

import elldimi.sdadvquerying.dao.IngredientRepository;
import elldimi.sdadvquerying.dao.LabelRepository;
import elldimi.sdadvquerying.dao.ShampooRepository;
import elldimi.sdadvquerying.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import utils.PrintUtil;

@Component
public class AppRunner implements CommandLineRunner {
    private final ShampooRepository shampooRepository;
    private final LabelRepository labelRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public AppRunner(ShampooRepository shampooRepository, LabelRepository labelRepository, IngredientRepository ingredientRepository) {
        this.shampooRepository = shampooRepository;
        this.labelRepository = labelRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.shampooRepository
                .findBySizeOrderById(Size.MEDIUM)
                .forEach(PrintUtil::printShampoo);
    }
}
