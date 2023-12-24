package catalogue;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

/**
 *BetterBasket is an extension of Basket which has two enhancements:
 *
 * ir merges multiple instances of the same product into a single record 
 * it sorts the record by ProductNum
 * 
 * @author  Faaruq Adegunwa
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable, Comparator<Product>

{
  private static final long serialVersionUID = 1L;

  @Override
  public boolean add (Product p1){
      // search existing products for matching record
      for (Product p2 : this){
          if (p1.getProductNum().equals(p2.getProductNum())){
            // found - update quantity and return
            p2.setQuantity(p2.getQuantity()+p1.getQuantity());
          return(true);
          }
      }
      //not found - add new product, using superclass method 
      super.add(p1);
      //now sort the list,
      //Many ways to do this - we turn a BetterBasket into a Comparator object,
      //and give it a compare method to order Products 
      Collections.sort(this, this);
      return(true); 
  }
  
  public int compare (Product p1, Product p2){
    return p1.getProductNum().compareTo(p2.getProductNum());
  }
}

  


