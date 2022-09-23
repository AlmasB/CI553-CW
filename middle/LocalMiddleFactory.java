/**
 * @author  Mike Smith University of Brighton
 * @version 2.1
 */

package middle;

import dbAccess.StockR;
import dbAccess.StockRW;
import orders.Order;


/**
  * Provide access to middle tier components.
  * Now only one instance of each middle tier object is created
  */

// Pattern: Abstract Factory

public class LocalMiddleFactory implements MiddleFactory
{
  private static StockR  aStockR  = null;
  private static StockRW aStockRW = null;
  private static Order   aOrder   = null;
  
  /**
   * Return an object to access the database for read only access.
   * All users share this same object.
   */
  
  public StockReader makeStockReader() throws StockException
  {
    if ( aStockR == null )
      aStockR = new StockR();
    return aStockR;
  }

  /**
   * Return an object to access the database for read/write access.
   * All users share this same object.
   */
  
  public StockReadWriter makeStockReadWriter() throws StockException
  {
    if ( aStockRW == null )
      aStockRW = new StockRW();
    return aStockRW;
  }
  
  /**
   * Return an object to access the order processing system.
   * All users share this same object.
   */
   
  public OrderProcessing makeOrderProcessing() throws OrderException
  {
    if ( aOrder == null )
      aOrder = new Order();
    return aOrder;
  }
}

