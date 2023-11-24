package catalogue;

import java.io.Serializable;
import java.util.Collections;

/**
 * Write a description of class BetterBasket here.
 * BetterBasket.java seeks to aid user interface for each instance of basket:
 * 	-several items of the same type can be combined into one request of the same product.
 * 	-insert a sorting method via product ID or soft via differing categories (i.e. alphabetical, chronological, numerical, etc)
 * 	
 * 
 * @author  Masroor Rahman 
 * @version 1.3
 */
public class BetterBasket extends Basket implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public boolean add(Product pr) {
 //Multiple items of the same type grouped here - use of try catch loop, for loop, or while loop
	  int i = 0;
	  boolean itemExists = false;
	  
	  while(i<this.size()) {
		  if(this.get(i).getProductNum().equals(pr.getProductNum())) {
			  //if item already exists, update quantity and set to true
			  this.get(i).setQuantity(this.get(i).getQuantity() + pr.getQuantity());
			  itemExists = true;
			  break; // loop exists as the item has been located
		  }
		  index++;
	  }
	  if(!itemExists) {
		  //if the item does not exist within basket, add it.
		  super.add(pr);
	  }
	  
	  //Sorts the basket oonce item has been added to the basket
	  Collections.sort(this, BetterBasket::sort);
	  
	  //adds product
	  return true;
  }
  
  //create method to check if item is within basket
  public boolean itemList(Product pr) {
	  for(int i=0;i<=this.size();i++) {
		  if(this.get(i).getProductNum().equals(pr.getProductNum())) {
			  //if statement, to first get product number, index++
			  return true;
		  }
	  }
	  return false;
  }

private static <T extends Object> int sort(T t1, T t2) {
	return 0;
}
  
}
