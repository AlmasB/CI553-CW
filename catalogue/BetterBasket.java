package catalogue;

import java.io.Serializable;
import java.util.Collections;
import java.util.*;

/**
 * Write a description of class BetterBasket here.
 * BetterBasket.java seeks to aid user interface for each instance of basket:
 * 	-several items of the same type can be combined into one request of the same product.
 * 	-insert a sorting method on BetterBasket via product ID or sort via differing categories (i.e. alphabetical, chronological, numerical, etc)
 * 	
 * 
 * @author  Masroor Rahman 
 * @version 1.3
 */
public class BetterBasket extends Basket implements Serializable
{
  private static final long serialVersionUID = 1L;
  
	public boolean add(Product p1) {
		//searching for existing products matching record
		for(Product p2: this) {
			if(p1.getProductNum().equals(p2.getProductNum())) {
				//been found, now update quantity and return prod
				p2.setQuantity(p2.getQuantity()+p1.getQuantity());
				return(true);
			}
		}
		super.add(p1);
		Collections.sort(this, BetterBasket::Sort);
		return true;
	}
	
	
	
//second attempt to create a better improved method for removing items from basket
	public boolean remove (Product p1) {
		//search for existing products matching 
		for (Product p2: this) {
			if(p1.getProductNum().equals(p2.getProductNum())) {
				//product found, decrement quantity 
				int newQuantity = p2.getQuantity() - p1.getQuantity();
				if (newQuantity < 0) {
					//Remove product if quantity is <= 0.
					this.remove(p2);
					return true;
				} else {
					//updates quantity, setting new amount
					p2.setQuantity(newQuantity);
					return true;
				}
			}
		}
		Collections.sort(this, BetterBasket::Sort);
		return false; 
		//product not detected within basket
	}
	
	
	
	
	public static int Sort (Product p1, Product p2) { 
		//the method is called by Collections.sort. Passes two parameters 
		//(product i and i+1), these are compared/switched and then returned 
		  return p1.getProductNum().compareTo(p2.getProductNum());
		  //compare to is passed as an int (+/-ve determined by results of comparison)
	  }
	
  

	  
	// create method to check if item is within basket
	public boolean itemList(Product pr) {
		for (int i = 0; i <= this.size(); i++) {
			if (this.get(i).getProductNum().equals(pr.getProductNum())) {
				// if statement, to first get product number, index++
				return true;
			}
		}
		return false;
	}

	
	private static <T extends Object> int sort(T t1, T t2) {
		return 0;
	}

}
