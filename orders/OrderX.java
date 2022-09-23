
package orders;

import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import middle.OrderException;
import middle.OrderProcessing;

import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Formatter;

/**
  * <BR>-----------------------------------------
  * <BR>ALTERNATIVE IMPLEMENTATION OF class Order
  * <BR>-----------------------------------------
  * <P>
  * The order processing system.<BR>
  * Manages the progression of customer orders, instances of a Basket as they are 
  * progressed through the system.
  * This is achieved by holding 3 seperate lists of orders, each list represents a specific
  * stage in the orders life cycle. These stages are:
  * <BR><B>Waiting to be processed<BR>
  * Curently being picked<BR>
  * Waiting to be collected<BR></B>
  * @author  Michael Alexander Smith
  * @version 2.0
  */
 
public class OrderX implements OrderProcessing
{
  private static int theNextNumber = 1;          // Start at 1
  // Orders entered but waiting to be processed (picked)
  private ArrayList<Basket>  theWaitingTray = new ArrayList<Basket>();

  // Orders being processed (currently being picked)
  private ArrayList<Basket>  theBeingPickedTray = new ArrayList<Basket>();

  // Orders waiting to be collected by the customer
  private ArrayList<Basket>  theToBeCollectedTray = new ArrayList<Basket>();

  /**
   * Used to generate debug information
   * @Param  basket an instance of a basket
   * @Return Description of contents
   */
  private String asString( Basket basket )
  {
    StringBuilder sb = new StringBuilder(1024);
    Formatter     fr = new Formatter(sb);
    fr.format( "#%d (", basket.getOrderNum() );
    for ( Product pr: basket )
    {
       fr.format( "%-15.15s: %3d ", pr.getDescription(), pr.getQuantity() );
    }
    fr.format( ")" );
    fr.close();
    return sb.toString();
  }

  /**
   * Generates a unique order number
   *   would be good to recycle numbers after 999
   * @return A unique order number
   */
  public synchronized int uniqueNumber()
  {
    return theNextNumber++;
  }

  /**
   * Add a new order to the order processing system
   * @param bought a new order that is to be processed
   */ 
  public synchronized void newOrder( Basket bought )
         throws OrderException
  {
    // You need to modify and fill in the correct code
    DEBUG.trace( "DEBUG: New order" );
    theWaitingTray.add( bought );
    for ( Basket bl : theWaitingTray )
    {
      DEBUG.trace( "Order: " + asString( bl ) );
    }
  }

  /**
   * Returns an order to pick from the warehouse.
   *  However if there is no order avilable then return null.
   * @return An order to pick.
   */

  public synchronized Basket getOrderToPick()
         throws OrderException
  {
    // You need to modify and fill in the correct code
    DEBUG.trace( "DEBUG: Get order to pick" );
    if ( theWaitingTray.size() > 0 )
    {
      Basket process = theWaitingTray.remove(0);
       theBeingPickedTray.add( process );
       return process;
    }
     return null;
  }

  /**
   * Informs the order processing system that the order has been
   * picked and the products are now being delivered to the
   * collection desk
   * @param  orderNum the order that has been picked
   * @return true :: Order in system, false -:: no such order
   */

  public synchronized boolean informOrderPicked( int orderNum )
         throws OrderException
  {
    // You need to modify and fill in the correct code
    DEBUG.trace( "DEBUG: Order picked [%d]", orderNum );
    for ( int i=0; i<theBeingPickedTray.size(); i++)
    {
      if ( theBeingPickedTray.get(i).getOrderNum() == orderNum )
      {
        Basket picked = theBeingPickedTray.remove(i);
        theToBeCollectedTray.add( picked );
        return true;
      }
    }
    return false;
  }

  /**
   * Informs the order processing system that the order has been
   * collected by the customer
   * @return true :: Order in system, false -:: no such order
   */

  public synchronized boolean informOrderCollected( int orderNum )
         throws OrderException
  {
    // You need to modify and fill in the correct code
    DEBUG.trace( "DEBUG: Order collected [%d]", orderNum );
    for ( int i=0; i<theToBeCollectedTray.size(); i++ )
    {
      if ( theToBeCollectedTray.get(i).getOrderNum() == orderNum )
      {
        theToBeCollectedTray.remove(i);
        return true;
      }
    }
    return false;
  }

  /**
   * Returns information about all the orders (order number) in the order processing system
   * This consists of a map with the orders in the 3 states:
   * "waiting to be processed", "curently being picked" and "waiting to be collected".
   *<PRE>
   * Key "Waiting"       A list of orders waiting to be processed
   * Key "BeingPicked"   A list of orders that are currently being picked
   * Key "ToBeCollected" A list of orders that can now be collected
   * </PRE>
   * @return a Map with the keys: Waiting, BeingPicked, ToBeCollected
   */

  public synchronized Map<String, List<Integer> > getOrderState()
         throws OrderException
  {
    DEBUG.trace( "DEBUG: get state of order system" );
    Map < String, List<Integer> > res = 
      new HashMap< String, List<Integer> >();
    res.put( "Waiting",       orderNos(theWaitingTray) );
    res.put( "BeingPicked",   orderNos(theBeingPickedTray) );
    res.put( "ToBeCollected", orderNos(theToBeCollectedTray) );

    return res;
  }
  
  private List< Integer > orderNos( ArrayList<Basket> queue )
  {
    List <Integer> res = new ArrayList<Integer>();
    for ( Basket sb: queue )
    {
      res.add( sb.getOrderNum() );
    }
    return res;
  }

}
