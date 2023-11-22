package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Improved version of the Basket class with recommended additions.
 * 
 * @author  Callum Barnett
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable
{
  private static final long serialVersionUID = 1L;
  

  @Override
  public boolean add(Product pr) {
	  // Check if product is repeated
	  for(Product existingProduct: this) {
		  if(existingProduct.getProductNum().equals(pr.getProductNum())) {
			  // Product is repeated, merge into single request
			  System.out.println("Product is repeated");
			  existingProduct.setQuantity(existingProduct.getQuantity()+ pr.getQuantity());
			  return true;
		  }
	  }
	  // Product is not repeated, continue
	  System.out.println("Product is not repeated");
	  super.add(pr);
	  return true;
  }
  // Sort ordered items into ascending order by product number.
  public void sortBasket() {
	  Collections.sort(this, (p1, p2) -> p1.getProductNum().compareTo(p2.getProductNum()));
  }
}
