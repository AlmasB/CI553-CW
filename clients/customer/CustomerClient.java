package clients.customer;

import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import com.sun.tools.javac.Main;
import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The standalone Customer Client
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
public class CustomerClient {
  public static void main (String args[])
  {

    String stockURL = args.length < 1         // URL of stock R
                    ? Names.STOCK_R           //  default  location
                    : args[0];                //  supplied location
    
    RemoteMiddleFactory mrf = new RemoteMiddleFactory();
    mrf.setStockRInfo( stockURL );
    displayGUI(mrf);                          // Create GUI


  }
   
  public static void displayGUI(MiddleFactory mf)
  {
    Advert advertWindow = new Advert(); // display advert window
    advertWindow.setVisible(true);
    JFrame  window = new JFrame();
    window.setTitle( "Customer Client (MVC RMI)" );
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    
    CustomerModel model = new CustomerModel(mf);
    CustomerView  view  = new CustomerView( window, mf, 0, 0 );
    CustomerController cont  = new CustomerController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Display Window

    view.backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        window.dispose();
        advertWindow.dispose();
        advertWindow.backgroundMusic.pausePlayer();
      }
    });
  }


}
