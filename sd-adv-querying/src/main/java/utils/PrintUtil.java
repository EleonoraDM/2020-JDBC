package utils;

import elldimi.sdadvquerying.entities.Ingredient;
import elldimi.sdadvquerying.entities.Shampoo;

import java.util.stream.Collectors;

public class PrintUtil {

    public static void printShampoo(Shampoo shampoo) {
        System.out.printf("|%5d |%-30.30s |%-8.8s |%8.2f |%-30.30s |%-35.35s |%n",
                shampoo.getId(), shampoo.getBrand(), shampoo.getSize(), shampoo.getPrice(),
                shampoo.getLabel().getId() + " - " + shampoo.getLabel().getTitle(),
                shampoo.getIngredients().stream().map(Ingredient::getName).collect(Collectors.joining(", ")));
    }

    public static void printIngredient(Ingredient ingredient) {
        System.out.printf("|%5d |%-30.30s |%5.2f |%n",
                ingredient.getId(), ingredient.getName(), ingredient.getPrice());
    }
}
