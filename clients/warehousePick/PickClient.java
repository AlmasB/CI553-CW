package clients.warehousePick;

import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;

/**
 * The standalone warehouse Pick Client.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
public class PickClient
{
   public static void main (String args[])
   {
     String stockURL = args.length < 1     // URL of stock RW
                     ? Names.STOCK_RW      //  default  location
                     : args[0];            //  supplied location
     String orderURL = args.length < 2     // URL of order
                     ? Names.ORDER         //  default  location
                     : args[1];            //  supplied location
     
    RemoteMiddleFactory mrf = new RemoteMiddleFactory();
    mrf.setStockRWInfo( stockURL );
    mrf.setOrderInfo  ( orderURL );        //
    displayGUI(mrf);                       // Create GUI
  }
  
  public static void displayGUI(MiddleFactory mf)
  {     
    JFrame  window = new JFrame();
     
    window.setTitle( "Pick Client (RMI MVC)");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    
    PickModel      model = new PickModel(mf);
    PickView       view  = new PickView( window, mf, 0, 0 );
    PickController cont  = new PickController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Display Screen
  }
}
