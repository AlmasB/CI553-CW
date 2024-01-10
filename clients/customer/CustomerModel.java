package clients.customer;

import catalogue.BetterBasket;
import catalogue.Music;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderException;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReader;


import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;
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
  


  private Music musicPlayer = new Music();

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
    musicPlayer.playMusic("C:/Users/zayan/OneDrive/Documents/Year 2/musicfile.wav");

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
  
 
  
  /**
   * A buy online function allowing user to buy product from client GUI
   */
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
  public void doCatalogue() {
      JFrame catalogueFrame = new JFrame("CATALOGUE ");
      JPanel panel = new JPanel(new BorderLayout());
      
      // Define catalogue items. Replace these items with actual product details.
      String[][] catalogueItems = {
          {"0001", "40 inch LED HD tv", "269.00"},
          {"0002", "DAB RADIO", "29.99"},
          {"0003", "TOASTER", "19.99"},
          {"0004" , "WATCH" ,"29.99"},
          {"0005" , "DIGITAL CAMERA" ,"89.99"},
          {"0006" , "MP3 PLAYER" ,"7.99"},
          {"0007" , "32GB USB2 DRIVE" ,"6.99"},
      };

      // Column headers for the table
      String[] columns = {"Product num", "ProductName", "Price", "Image"};

      // Create a table model with the data and column names
      DefaultTableModel model = new DefaultTableModel(catalogueItems, columns) {
          @Override
          public Class<?> getColumnClass(int column) {
              if (column < 3) {
                  return String.class;
              } else {
                  return ImageIcon.class;
              }
          }
      };

      // Initialize the table with the model
      JTable catalogueTable = new JTable(model);
      catalogueTable.setRowHeight(100); // Set row height to accommodate images

      // Load images for each product
      for (int i = 0; i < catalogueItems.length; i++) {
          String productNum = catalogueItems[i][0];
          ImageIcon image = getProductImage(productNum);
          model.setValueAt(image, i, 3);
      }

      // Add the table to a JScrollPane and then to the panel
      JScrollPane scrollPane = new JScrollPane(catalogueTable);
      panel.add(scrollPane);

      // Configure and display the JFrame
      catalogueFrame.add(panel);
      catalogueFrame.setSize(600, 400);
      catalogueFrame.setVisible(true);
  }

  /**
   * Retrieves an ImageIcon for a given product number.
   * @param productNum The product number for which to fetch the image.
   * @return ImageIcon of the product or null if not available.
   */
  private ImageIcon getProductImage(String productNum) {
      try {
          return theStock.getImage(productNum); // Fetch the image using the stock reader
      } catch (StockException e) {
          DEBUG.error("CustomerModel.getProductImage()\n%s", e.getMessage());
          return null; // Return null if an exception occurs
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

  

	 
	      
	    
	 
	

	



