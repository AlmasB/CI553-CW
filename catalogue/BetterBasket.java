package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

/**
 * Write a description of class BetterBasket here.
 * 
 * @author  Your Name 
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable
{
  private static final long serialVersionUID = 1L;
 // @Override
  public boolean addProduct(Product pr) {
	  
	  for(Product exsistingProduct:this){
		  if(exsistingProduct.getProductNum().equals(pr.getProductNum())) {
			  
		
			exsistingProduct.setQuantity(exsistingProduct.getQuantity()+ pr.getQuantity());
		  
		  return (true);
	  }
		  
	  
	  
  }
  
  super.add(pr);
  
  Collections.sort(this, new ProductComparator());
  return (true);
}
  
private static class ProductComparator implements Comparator<Product> {
  @Override
public int compare(Product pr1, Product pr2) {
	
	return pr1.getProductNum().compareTo(pr2.getProductNum());
}
}

  // You need to add code here
}
