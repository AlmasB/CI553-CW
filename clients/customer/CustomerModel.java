package clients.customer;

import catalogue.Basket;
import catalogue.Product;
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
  private Product     theProduct = null;          // Current product
  private Basket      theBasket  = null;          // Bought items

  private String      pn = "";                    // Product being processed

  private StockReader     theStock     = null;
  private OrderProcessing orderProcessing;
  private ImageIcon       thePic       = null;

  /*
   * Construct the model of the Customer
   * @param mf The factory to create the connection objects
   */
  public CustomerModel(MiddleFactory mf)
  {
    try                                          // 
    {  
      theStock = mf.makeStockReader();           // Database access
      orderProcessing = mf.makeOrderProcessing();
    } catch ( Exception e )
    {
      DEBUG.error("CustomerModel.constructor\n" +
                  "Database not created?\n%s\n", e.getMessage() );
    }
    theBasket = makeBasket();                    // Initial Basket
  }
  
  /**
   * return the Basket of products
   * @return the basket of products
   */
  public Basket getBasket()
  {
    return theBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   */
  public void doCheck(String productNum )
  {
    String theAction = "";
    pn  = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theStock.exists( pn ) )              // Stock Exists?
      {                                         // T
        theProduct = theStock.getDetails( pn ); //  Product
        if (theProduct.getQuantity() >= amount )       //  In stock?
        { 
          theAction =                           //   Display 
            String.format( "%s : %7.2f (%2d) ", //
            		theProduct.getDescription(),              //    description
            		theProduct.getPrice(),                    //    price
            		theProduct.getQuantity() );               //    quantity
          theProduct.setQuantity( amount );             //   Require 1
          thePic = theStock.getImage( pn );     //    product
        } else {                                //  F
          theAction =                           //   Inform
        		  theProduct.getDescription() +               //    product not
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
    setChanged(); 
    notifyObservers(theAction);
  }

  /**
   * Clear the products from the basket
   */
  public void doClear()
  {
    String theAction = "";
    theBasket.clear();                        // Clear s. list
    theAction = "Enter Product Number";       // Set display
    theProduct = null; // Clear last checked item
    thePic = null;                            // No picture
    setChanged(); 
    notifyObservers(theAction);
  }
  
  public void addToBasket() {
	  String action;
	  if(theProduct == null) {
		  // Product must not be null, inform the user they have not queried an item
		  action = "Nothing to add to basket!";
	  } else {
		  theBasket.add(theProduct);
		  action = theProduct.getDescription() + " added to basket!";
	  }
	  
	  setChanged();
	  notifyObservers(action);
  }
  
  public void removeFromBasket() {
	  
  }
  
  public void buyOnline() {
	  String result;
	  try {
		  int orderNumber = orderProcessing.uniqueNumber();
		  theBasket.setOrderNum(orderNumber);
		  DEBUG.trace("Basket: " + theBasket.getDetails());
		  orderProcessing.newOrder(theBasket);
		  result = "Order purchased, order no: " + orderNumber;
	  } catch(OrderException e) {
		  DEBUG.error("%s\n%s", "CashierModel.doCancel", e.getMessage());
		  setChanged(); 
		  notifyObservers(e.getMessage()); // Notify
		  return;
	  }
	  
	  // Must make a new basket here as some code may rely on this basket object.
	  theBasket = makeBasket();
	  setChanged();
	  notifyObservers(result);
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
   * ask for update of view callled at start
   */
  private void askForUpdate()
  {
    setChanged(); notifyObservers("START only"); // Notify
  }

  /**
   * Make a new Basket
   * @return an instance of a new Basket
   */
  protected Basket makeBasket()
  {
    return new Basket();
  }
}

