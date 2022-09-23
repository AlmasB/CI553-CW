package middle;

import catalogue.Basket;
import debug.DEBUG;
import remote.RemoteOrder_I;

import java.rmi.Naming;
import java.util.List;
import java.util.Map;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

/**
 * Facade for the order processing handling which is implemented on the middle tier.
 * This code is incomplete
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

public class F_Order implements OrderProcessing
{
  private RemoteOrder_I aR_Order    = null;
  private String        theOrderURL = null;

  public F_Order(String url)
  {
    theOrderURL = url;
  }
  
  private void connect() throws OrderException
  {
    try                                            // Setup
    {                                              //  connection
      aR_Order =                                   //  Connect to
       (RemoteOrder_I) Naming.lookup(theOrderURL); // Stub returned
    }
    catch ( Exception e )                          // Failure to
    {                                              //  attach to the
      aR_Order = null;
      throw new OrderException( "Com: " + 
                               e.getMessage()  );  //  object
      
    }
  }


  public void newOrder( Basket bought )
         throws OrderException
  {
    DEBUG.trace("F_Order:newOrder()" );
    try
    {
      if ( aR_Order == null ) connect();
      aR_Order.newOrder( bought );
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  public int uniqueNumber()
         throws OrderException
  {
    DEBUG.trace("F_Order:uniqueNumber()" );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.uniqueNumber();
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Returns an order to pick from the warehouse
   * if no order then returns null.
   * @return An order to pick
   */

  public synchronized Basket getOrderToPick()
         throws OrderException
  {
    DEBUG.trace("F_Order:getOrderTioPick()" );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.getOrderToPick();
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Informs the order processing system that the order has been
   * picked and the products are now on the conveyor belt to
   * the shop floor.
   */

  public synchronized boolean informOrderPicked( int orderNum )
         throws OrderException
  {
    DEBUG.trace("F_Order:informOrderPicked()" );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.informOrderPicked(orderNum);
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Informs the order processing system that the order has been
   * collected by the customer
   */

  public synchronized boolean informOrderCollected( int orderNum )
         throws OrderException
  {
    DEBUG.trace("F_Order:informOrderCollected()" );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.informOrderCollected(orderNum);
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }

  /**
   * Returns information about all orders in the order processing system
   */

  public synchronized Map<String, List<Integer> > getOrderState()
         throws OrderException
  {
    DEBUG.trace("F_Order:getOrderState()" );
    try
    {
      if ( aR_Order == null ) connect();
      return aR_Order.getOrderState();
    } catch ( Exception e )
    {
      aR_Order = null;
      throw new OrderException( "Net: " + e.getMessage() );
    }
  }
}
