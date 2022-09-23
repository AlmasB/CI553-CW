package clients.collection;

import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;

import java.util.Observable;

/**
 * Implements the Model of the collection client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class CollectModel extends Observable
{
  private String      theAction   = "";
  private String      theOutput   = "";
  private OrderProcessing theOrder     = null;

  /*
   * Construct the model of the Collection client
   * @param mf The factory to create the connection objects
   */
  public CollectModel(MiddleFactory mf)
  {
    try                                           // 
    {      
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      DEBUG.error("%s\n%s",
       "CollectModel.constructor\n%s", 
        e.getMessage() );
    }
  }
  
  /**
   * Check if the product is in Stock
   * @param orderNumber The order to be collected
   */
  public void doCollect(String orderNumber )
  {
    int orderNum = 0;
    String on  = orderNumber.trim();         // Product no.
    try
    {
      orderNum = Integer.parseInt(on);       // Convert
    }
    catch ( Exception err) 
    {
      // Convert invalid order number to 0
    }
    try
    {
      boolean ok = 
       theOrder.informOrderCollected( orderNum );
      if ( ok )
      {
        theAction = "";
        theOutput = "Collected order #" + orderNum;
      }
      else
      {
        theAction = "No such order to be collected : " + orderNumber;
        theOutput = "No such order to be collected : " + orderNumber;
      }
    } catch ( Exception e )
    {
      theOutput = String.format( "%s\n%s",
                   "Error connection to order processing system",
                   e.getMessage() );
      theAction = "!!!Error";
    }
    setChanged(); notifyObservers(theAction);
  }

  /**
   * The output to be displayed
   * @return The string to be displayed
   */
  public String getResponce()
  {
    return theOutput;
  }
  
}
