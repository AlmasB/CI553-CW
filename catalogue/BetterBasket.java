package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Improved version of the "Basket" class.
 * 
 * @author  Callum Barnett
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable
{
  private static final long serialVersionUID = 1L;
  

  // You need to add code here
  @Override
  public boolean add(Product pr) {
	  for(Product existingProduct: this) {
		  if(existingProduct.getProductNum().equals(pr.getProductNum())) {
			  System.out.println("Product exists");
			  existingProduct.setQuantity(existingProduct.getQuantity()+ pr.getQuantity());
			  return true;
		  }
	  }
	  
	  System.out.println("Product doesn't exist");
	  super.add(pr);
	  return true;
  }
  
  public void sortBasket() {
	  Collections.sort(this, (p1, p2) -> p1.getProductNum().compareTo(p2.getProductNum()));
  }
}
