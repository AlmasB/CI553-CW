package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;




public class BetterBasket extends Basket implements Serializable {

    private static final long serialVersionUID = 1L;
    


    @Override
    public boolean add(Product pr) {
        // Check if the product is already in the basket
        boolean found = false;
        for (Product existingProduct : this) {
            if (existingProduct.getProductNum().equals(pr.getProductNum())) {
                // If the product is found, merge it with existing product and exit the loop
                existingProduct.setQuantity(existingProduct.getQuantity() + pr.getQuantity());
                found = true;
                break;
            }
        }

        // If the product is not found, add it to the basket
        if (!found) {
            super.add(pr);
        }

        // Sort the items in ascending order of product numbers
        Collections.sort(this, Comparator.comparing(Product::getProductNum));

        return true; // Return true as in your original code
    }
}
