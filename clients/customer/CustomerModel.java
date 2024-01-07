package clients.customer;

import catalogue.BetterBasket;
import catalogue.Product;
import clients.cashier.BetterCashierModel;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderException;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReader;

import javax.swing.*;
import java.util.Observable;

/**
 * Implements the Model of the customer client
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */
public class CustomerModel extends Observable
{
protected enum State { process, checked }

  protected State       theState   = State.process;   // Current state
  protected Product     theProduct = null;          // Current product
  protected BetterBasket      theBetterBasket  = null;          // Bought items


  private String      pn = "  ";                    // Product being processed

  protected StockReader     theStock     = null;
  protected OrderProcessing theOrder     = null;
  protected ImageIcon       thePic       = null;

  /*
   * Construct the model of the Customer
   * @param mf The factory to create the connection objects
   */
  public CustomerModel(MiddleFactory mf)
  {
    try                                          // 
    {  
      theStock = mf.makeStockReader();           // Database access
      theOrder = mf.makeOrderProcessing();       // Initialize theOrder

    } catch ( Exception e )
    {
      DEBUG.error("CustomerModel.constructor\n" +
                  "Database not created?\n%s\n", e.getMessage() );
    }
    theBetterBasket = makeBetterBasket();                    // Initial Basket
  }
  
  /**
   * return the Basket of products
   * @return the basket of products
   */
  public BetterBasket getBetterBasket()
  {
    return theBetterBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   */
  public void doCheck(String productNum )
  {
    theBetterBasket.clear();                          // Clear s. list
    String theAction = "";
    pn  = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theStock.exists( pn ) )              // Stock Exists?
      {                                         // T
        Product pr = theStock.getDetails( pn ); //  Product
        if ( pr.getQuantity() >= amount )       //  In stock?
        { 
          theAction =                           //   Display 
            String.format( "%s : %7.2f (%2d) ", //
              pr.getDescription(),              //    description
              pr.getPrice(),                    //    price
              pr.getQuantity() );               //    quantity
          pr.setQuantity( amount );             //   Require 1
          theBetterBasket.add( pr );                  //   Add to basket
          thePic = theStock.getImage( pn );     //    product
        } else {                                //  F
          theAction =                           //   Inform
            pr.getDescription() +               //    product not
            " not in stock" ;                   //    in stock
        }
      } else {                                  // F
        theAction =                             //  Inform Unknown
          "Unknown product number " + pn;       //  product number
      }
    } catch( StockException e )
    {
      DEBUG.error("CustomerClient.doCheck()\n%s",
      e.getMessage() );
    }
    setChanged(); notifyObservers(theAction);
  }

  /**
   * Clear the products from the basket
   */
  public void doClear()
  {
    String theAction = "";
    theBetterBasket.clear();                        // Clear s. list
    theAction = "Enter Product Number";       // Set display
    thePic = null;                            // No picture
    setChanged(); notifyObservers(theAction);
  }
  public void doBuyOnline() {
	  String theAction = "";
	    try {
	        if (theBetterBasket == null || theBetterBasket.isEmpty()) {
	            throw new OrderException("Basket is empty");
	        }

	        if (theState != State.checked) {  // Ensure the state is checked
	             theAction ="Check if OK with customer first";
	        }

	        // Check stock availability for each product in the basket
	        for (Product product : theBetterBasket) {
	            if (!theStock.exists(product.getProductNum()) || 
	                theStock.getDetails(product.getProductNum()).getQuantity() < product.getQuantity()) {
	                throw new StockException("Insufficient stock for product: " + product.getDescription());
	            }
	        }

	        // Generate and set the order number
	        int orderNumber = theOrder.uniqueNumber();
	        theBetterBasket.setOrderNum(orderNumber);

	        theOrder.newOrder(theBetterBasket); // Process the order

	        theBetterBasket.clear(); // Clear the basket after the order is placed
	        theState = State.process; // Reset the state
	        setChanged();
	        notifyObservers("Order placed successfully with order number: " + orderNumber);
	    }
	    catch (OrderException | StockException e) {
	        DEBUG.error("CustomerModel.doBuyOnline()\n%s", e.getMessage());
	        setChanged();
	        notifyObservers("Error: " + e.getMessage());
	    }
	}


 
  /**
   * Return a picture of the product
   * @return An instance of an ImageIcon
   */ 
  public ImageIcon getPicture()
  {
    return thePic;
  }
  
  /**
   * ask for update of view called at start
   */
  public void askForUpdate()
  {
    setChanged(); notifyObservers("START only"); // Notify
  }

  /**
   * Make a new Basket
   * @return an instance of a new Basket
   */
  protected BetterBasket makeBetterBasket()
  {
    return new BetterBasket();
  }

  public void askForUpdate1(){
  {
    setChanged(); notifyObservers("Welcome");}}


	
}

  

	 
	      
	    
	 
	

	



