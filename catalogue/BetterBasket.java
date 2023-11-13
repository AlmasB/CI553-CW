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
  
  	@Override 
  	public boolean add(Product p1) {
  		for(Product p2 : this) {
  			if(p1.getProductNum().equals(p2.getProductNum())) {
  				p2.setQuantity(p2.getQuantity() + p1.getQuantity());
  				sortByQuantity();
  				return true;
  			}
  		}
  		super.add(p1);
  		sortByQuantity();
  		
  		return true;
  	}
  	
  public void sortByQuantity() {
	  Collections.sort(this, new Comparator<>() {
			public int compare(Product p1, Product p2) {
				System.out.println(p1.getQuantity());
				System.out.println(p2.getQuantity());
		  		return (p2.getQuantity() + "").compareTo(p1.getQuantity() + "")  ;
		  	}
			
		});
  }
 
}
