package catalogue;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BetterBasketTest {

    private BetterBasket basket;

    @BeforeEach
    public void setUp() {
        basket = new BetterBasket();
    }

    @Test
    public void testAddNewProduct() {
        Product newProduct = new Product("123", "Description", 10.0, 1);
        assertTrue(basket.add(newProduct));
        assertEquals(1, basket.size());
        assertEquals(newProduct, basket.get(0));
    }

    @Test
    public void testAddExistingProductIncreasesQuantity() {
        Product existingProduct = new Product("123", "Description", 10.0, 1);
        basket.add(existingProduct);

        Product sameProduct = new Product("123", "Description", 10.0, 2);
        assertTrue(basket.add(sameProduct));
        assertEquals(1, basket.size());
        assertEquals(3, basket.get(0).getQuantity());
    }

    @Test
    public void testProductsAreSortedByProductNumber() {
        Product product1 = new Product("123", "Desc1", 10.0, 1);
        Product product2 = new Product("456", "Desc2", 20.0, 1);
        Product product3 = new Product("789", "Desc3", 30.0, 1);

        basket.add(product2);
        basket.add(product3);
        basket.add(product1);

        assertEquals("123", basket.get(0).getProductNum());
        assertEquals("456", basket.get(1).getProductNum());
        assertEquals("789", basket.get(2).getProductNum());
    }
}
