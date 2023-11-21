package catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;

/**
 * A collection of products from the CatShop.
 *  used to record the products that are to be/
 *   wished to be purchased.
 * @author  Mike Smith University of Brighton
 * @version 2.2
 *
 */
public class Basket extends ArrayList<Product> implements Serializable
{
  private static final long serialVersionUID = 1;
  private int    theOrderNum = 0;          // Order number
  
  /**
   * Constructor for a basket which is
   *  used to represent a customer order/ wish list
   */
  public Basket()
  {
    theOrderNum  = 0;
  }
  
  /**
   * Constructor for a basket which takes another baskets
   * items and adds them to this.
   * @param collection the other basket to add
   */
  public Basket(Collection<Product> collection) {
	  super(collection);
  }
  
  /**
   * Set the customers unique order number
   * Valid order Numbers 1 .. N
   * @param anOrderNum A unique order number
   */
  public void setOrderNum( int anOrderNum )
  {
    theOrderNum = anOrderNum;
  }

  /**
   * Returns the customers unique order number
   * @return the customers order number
   */
  public int getOrderNum()
  {
    return theOrderNum;
  }
  
  /**
   * Add a product to the Basket.
   * Product is appended to the end of the existing products
   * in the basket.
   * Unless the list already contains the product, in which case the product quantity
   * is increased instead.
   * @param pr A product to be added to the basket
   * @return true if successfully adds the product
   */
  // Will be in the Java doc for Basket
  @Override
  public boolean add(Product pr) {
	  // Check if product is already in the list
	  Optional<Product> optional = stream()
			  .filter(productInList -> pr.isSameItem(productInList))
			  .findFirst();
	  
	  // If the product is already in the basket, increase the quantity of the product
	  // instead of adding it to the list again
	  if(optional.isPresent()) {
		  Product inList = optional.get();
		  inList.setQuantity(inList.getQuantity() + pr.getQuantity());
		  return true;
	  } else {
		  return super.add(pr.copy());     // Call add in ArrayList
	  }
  }
  
  @Override
	public boolean addAll(Collection<? extends Product> c) {
		boolean changedFlag = false;
		for(Product p : c) {
			changedFlag = add(p) ? true : changedFlag;
		}
		return changedFlag;
	}
  
  @Override
	public boolean remove(Object o) {
	  if(!(o instanceof Product)) return false;
	  
	  // The object passed will not be the same as the one stored in list,
	  // as a copy is made before adding the product to the list,
	  // therefore we need to check by item type
	  Optional<Product> optional = stream()
			  .filter(productInList -> productInList.isSameItem((Product) o))
			  .findFirst();
	  
	  // Product was not found in the list
	  if(optional.isEmpty()) return false;
	  
	  // Removes the item
	  return super.remove(optional.get());
	}
  
  /**
   * Will decrease the quantity of the product in the list by one
   * If the quantity of the product becomes zero, the product will be removed from the list
   * @param pr - the product to decrease
   * @return true if the product was removed or the quantity decreased
   */
  public boolean decreaseProductQuantity(Product pr) {
	  // Find the product in the list that matches this product
	  Optional<Product> optional = stream()
			  .filter(productInList -> pr.isSameItem(productInList))
			  .findFirst();
	  
	  // Product is not in the list, so could not be decreased
	  if(optional.isEmpty()) return false;
	  
	  Product productInList = optional.get();
	  // Check if quantity is already one (if so, remove)
	  if(productInList.getQuantity() <= 1) {
		  return remove(productInList);
	  }
	  
	  // Decrease count and return true
	  productInList.setQuantity(productInList.getQuantity() - 1);
	  return true;
  }

  /**
   * Returns a description of the products in the basket suitable for printing.
   * @return a string description of the basket products
   */
  public String getDetails()
  {
    Locale uk = Locale.UK;
    StringBuilder sb = new StringBuilder(256);
    Formatter     fr = new Formatter(sb, uk);
    String csign = (Currency.getInstance( uk )).getSymbol();
    double total = 0.00;
    if ( theOrderNum != 0 )
      fr.format( "Order number: %03d\n", theOrderNum );
      
    if ( this.size() > 0 )
    {
      for ( Product pr: this )
      {
        int number = pr.getQuantity();
        fr.format("%-7s",       pr.getProductNum() );
        fr.format("%-14.14s ",  pr.getDescription() );
        fr.format("(%3d) ",     number );
        fr.format("%s%7.2f",    csign, pr.getPrice() * number );
        fr.format("\n");
        total += pr.getPrice() * number;
      }
      fr.format("----------------------------\n");
      fr.format("Total                       ");
      fr.format("%s%7.2f\n",    csign, total );
      fr.close();
    }
    return sb.toString();
  }
  
  public BasketDetails getBasketDetails() {
	  return new BasketDetails(this);
  }
}
