package clients.customer;

import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import events.Listener;
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
  private Product     product = null;          // Current product
  private Basket      basket  = null;          // Bought items
  private Listener<Basket> basketChangeListener;

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
    basket = makeBasket();                    // Initial Basket
  }
  
  /**
   * return the Basket of products
   * @return the basket of products
   */
  public Basket getBasket()
  {
    return basket;
  }
  
  public Product getProduct() {
	return product;
}
  
  public void setBasketChangeListener(Listener<Basket> basketChangeListener) {
	  this.basketChangeListener = basketChangeListener;
  }
  
  public void processCheck(String productNumber) {
	  try {
		  if(!theStock.exists(productNumber)) {
			  return;
		  }
		  
		  doCheck(productNumber);
	  } catch(StockException e) {
		  DEBUG.error("CustomerClient.processCheck()\n%s", e.getMessage());
	  }
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
        product = theStock.getDetails( pn ); //  Product
        if (product.getQuantity() >= amount )       //  In stock?
        { 
          theAction =                           //   Display 
            String.format( "%s : %7.2f (%2d) ", //
            		product.getDescription(),              //    description
            		product.getPrice(),                    //    price
            		product.getQuantity() );               //    quantity
          product.setQuantity( amount );             //   Require 1
          thePic = theStock.getImage( pn );     //    product
        } else {                                //  F
          theAction =                           //   Inform
        		  product.getDescription() +               //    product not
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
    basket.clear();                        // Clear s. list
    theAction = "Enter Product Number";       // Set display
    product = null; // Clear last checked item
    thePic = null;                            // No picture
    setChanged(); 
    notifyObservers(theAction);
  }
  
  public void addToBasket() {
	  String action;
	  if(product == null) {
		  // Product must not be null, inform the user they have not queried an item
		  action = "Nothing to add to basket!";
	  } else {
		  basket.add(product);
		  action = product.getDescription() + " added to basket!";
	  }
	  
	  basketChangeListener.onChange(basket);
	  
	  setChanged();
	  notifyObservers(action);
  }
  
  public void removeFromBasket() {
	  
  }
  
  /**
   * The customer client can buy the contents of their basket,
   * bypassing the cashier and going straight to picking.
   */
  public void buyOnline() {
	  String result;
	  if(basket.isEmpty()) {
		  result = "Basket is empty";
		  setChanged();
		  notifyObservers(result);
		  return;
	  }
	  
	  try {
		  int orderNumber = orderProcessing.uniqueNumber();
		  basket.setOrderNum(orderNumber);
		  DEBUG.trace("Basket: " + basket.getDetails());
		  orderProcessing.newOrder(basket);
		  result = "Order purchased, order no: #" + orderNumber;
	  } catch(OrderException e) {
		  DEBUG.error("%s\n%s", "CashierModel.doCancel", e.getMessage());
		  setChanged(); 
		  notifyObservers(e.getMessage()); // Notify
		  return;
	  }
	  
	  // Must make a new basket here as some code may rely on this basket object.
	  basket = makeBasket();
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

