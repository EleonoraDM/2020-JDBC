package elldimi.sdadvquerying.init;

import elldimi.sdadvquerying.dao.IngredientRepository;
import elldimi.sdadvquerying.dao.LabelRepository;
import elldimi.sdadvquerying.dao.ShampooRepository;
import elldimi.sdadvquerying.entities.Ingredient;
import elldimi.sdadvquerying.entities.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import utils.PrintUtil;

import javax.transaction.Transactional;

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
    @Transactional
    public void run(String... args) throws Exception {

        //1.	Select Shampoos by Size
        this.shampooRepository
                .findBySizeOrderById(Size.MEDIUM)
                .forEach(PrintUtil::printShampoo);
        System.out.println("7--------------------------------------------------------------------------------------------------------------------------------");

        //7.	Select Shampoos by Ingredients
        this.shampooRepository
                .findWithIngredientsIn("Apple")
                .forEach(PrintUtil::printShampoo);
        System.out.println("2--------------------------------------------------------------------------------------------------------------------------------");

        //2.	Select Shampoos by Size or Label
        this.shampooRepository
                .findBySizeOrLabelIdOrderByPrice(Size.MEDIUM, (long) 10)
                .forEach(PrintUtil::printShampoo);
        System.out.println("3--------------------------------------------------------------------------------------------------------------------------------");

        //3.	Select Shampoos by Price
        this.shampooRepository
                .findByPriceGreaterThanOrderByPriceDesc(5.00)
                .forEach(PrintUtil::printShampoo);
        System.out.println("4--------------------------------------------------------------------------------------------------------------------------------");

        //4.	Select Ingredients by Name
        this.ingredientRepository
                .findByNameStartingWith("M")
                .forEach(PrintUtil::printIngredient);
        System.out.println("5--------------------------------------------------------------------------------------------------------------------------------");

        //5.	Select Ingredients by Names
        this.ingredientRepository
                .findByNameIn("Lavender", "Herbs", "Apple")
                .forEach(PrintUtil::printIngredient);
        System.out.println("6--------------------------------------------------------------------------------------------------------------------------------");

        //6.	Count Shampoos by Price
        System.out.printf("Count of shampoos: %d%n",
                (long) this.shampooRepository
                        .findByPriceLessThan(8.50)
                        .size());
        System.out.println("8--------------------------------------------------------------------------------------------------------------------------------");

        //8.	Select Shampoos by Ingredients Count
        this.shampooRepository
                .findAllByIngredientsCount(2)
                .forEach(PrintUtil::printShampoo);
        System.out.println("9-------------------------------------------------------------------------------------------------------------------------------");

        //9.	Delete Ingredients by Name
        String ingName = "Apple";

        int shampoosCount =
                this.shampooRepository
                        .findWithIngredientsIn(ingName).size();

        Ingredient ingToDelete = this.ingredientRepository.findByName(ingName).get();
        this.shampooRepository
                .findWithIngredientsIn(ingName)
                .forEach(s -> s.getIngredients().remove(ingToDelete));

        int deletedIngs = this.ingredientRepository.deleteAllByName(ingName);

        System.out.printf("Deleted %d ingredient/-s with the given name! Removed from %d shampoos!%n",
                deletedIngs, shampoosCount);
        System.out.println("11 before------------------------------------------------------------------------------------------------------------------------");

        //11.	Update Ingredients by Names
        this.ingredientRepository
                .findByNameIn("Lavender", "Herbs", "Apple")
                .forEach(PrintUtil::printIngredient);
        this.ingredientRepository
                .updatePriceOfIngredientsInList(1.80, "Lavender", "Herbs", "Apple");
        System.out.println("11 after-------------------------------------------------------------------------------------------------------------------------");
        this.ingredientRepository
                .findByNameIn("Lavender", "Herbs")
                .forEach(PrintUtil::printIngredient);
    }
}
