package clients.settings;

import javax.swing.JFrame;

import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;

/**
 * The settings menu
 * @author  Callum Barnett
 * @version 1.0
 */

public class SettingsClient {

	   public static void main (String args[])
	   {
	    RemoteMiddleFactory mrf = new RemoteMiddleFactory();     
	    displayGUI(mrf);                       // Create GUI
	  }

	  private static void displayGUI(MiddleFactory mf)
	  {     
	    JFrame  window = new JFrame();
	     
	    window.setTitle( "Cashier Client (MVC RMI)");
	    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    
	    SettingsModel      model = new SettingsModel(mf);
	    SettingsView       view  = new SettingsView( window, mf, 0, 0 );
	    SettingsController cont  = new SettingsController( model, view );
	    view.setController( cont );

	    model.addObserver( view );       // Add observer to the model
	    window.setVisible(true);         // Display Screen
	    model.askForUpdate();
	  }
	
}
