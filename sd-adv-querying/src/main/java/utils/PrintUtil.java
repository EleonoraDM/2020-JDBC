package utils;

import elldimi.sdadvquerying.entities.Shampoo;

public class PrintUtil {

    public static void printShampoo(Shampoo shampoo) {
        System.out.printf("|%5d | %-30.30s |%-8.8s |%8.2f | %-40.40s |%n",
                shampoo.getId(), shampoo.getBrand(), shampoo.getSize(), shampoo.getPrice(),
                shampoo.getLabel().getTitle() + " - " + shampoo.getLabel().getSubtitle());
    }
}
