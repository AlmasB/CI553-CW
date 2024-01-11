package catalogue;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BetterBasketTest {

	@Test
	void testAdd() {
		BetterBasket b = new BetterBasket();
	    
		Product p1 = new Product("0002", "Toaster", 10.00, 1);
	
		Product p2 = new Product ("0003", "Kettle", 15.00, 1);
		
		Product p3 = new Product ("0001", "Microwave", 15.00, 1);
		
		
		b.add(p1);  
		b.add(p2);
		b.add(p3);
		
		
		assertEquals(3, b.size());               //Checking number of different items are in basket
		
			
		assertEquals("0001", b.get(0).getProductNum());  //Checking items are ordered correctly 
		assertEquals("0002", b.get(1).getProductNum());
		assertEquals("0003", b.get(2).getProductNum());
		
		assertEquals(1, b.get(0).getQuantity());        //Microwave
		assertEquals(1, b.get(1).getQuantity());        //Toaster
		assertEquals(1, b.get(2).getQuantity());        //Kettle
		
		b.add(new Product("0001", "Microwave", 15.00, 2));          //new items added to basket
		b.add(new Product("0002", "Toaster", 10.00, 3));
		
		assertEquals(3, b.get(0).getQuantity());                   //Check merge
		assertEquals(1, b.get(2).getQuantity());
		
		
	}

}
