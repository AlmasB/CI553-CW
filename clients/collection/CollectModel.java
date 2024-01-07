package clients.collection;

import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
      
      try {
          boolean ok = theOrder.informOrderCollected(orderNum);
          if (ok) {
              theAction = "";
              theOutput = "Collected order #" + orderNum;

              writeReceipt(orderNum); // This line writes the receipt
              
              // Notify observers about the successful collection
              setChanged();
              notifyObservers(theAction);
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
  
    
    public void writeReceipt(int orderNumber) {
        File file = new File("OrderInfo/Orders/Order.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);

            // Formatting the receipt content
            out.println("======================================");
            out.printf("RECEIPT FOR ORDER: %d\n", orderNumber);
            out.println("--------------------------------------");
            out.printf("Collected on: %s\n", dtf.format(now));
            out.println("======================================\n");

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
  
  public void doReceipt() {
	  SwingUtilities.invokeLater(() -> {
          try {
              File fileToOpen = new File("OrderInfo/Orders/Order.txt"); // Replace with your file path
              if (fileToOpen.exists()) {
                  Desktop.getDesktop().open(fileToOpen);
              } else {
                  JOptionPane.showMessageDialog(null,
                          "Receipt file not found.", "Error",
                          JOptionPane.ERROR_MESSAGE);
              }
          } catch (IOException exception) {
              JOptionPane.showMessageDialog(null,
                      "An error occurred while opening the file: " + exception.getMessage(),
                      "Error", JOptionPane.ERROR_MESSAGE);
          }
      });
	  
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
