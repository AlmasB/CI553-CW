package middle;

import catalogue.Basket;

import java.util.List;
import java.util.Map;

/**
  * Defines the interface for accessing the order processing system.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */

public interface OrderProcessing
{
                                                   // Used by
  public void newOrder(Basket bought)              // Cashier
         throws OrderException;

  public int  uniqueNumber()                       // Cashier
         throws OrderException;
   
  public Basket getOrderToPick()                   // Picker
         throws OrderException;
 
  public boolean informOrderPicked(int orderNum)   // Picker 
         throws OrderException;
         
  public boolean informOrderCollected(int orderNum) // Collection
         throws OrderException;
         
  public Map<String,List<Integer>> getOrderState() // Display
         throws OrderException;
}
