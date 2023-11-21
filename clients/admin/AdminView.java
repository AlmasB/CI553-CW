package clients.admin;

import java.awt.Container;
import java.awt.Font;

import javax.swing.RootPaneContainer;

import middle.MiddleFactory;
import middle.admin.EmployeeManager;

public class AdminView {
	
	private static final int H = 300;       // Height of window pixels
	private static final int W = 400;       // Width  of window pixels
	
	private EmployeeManager employeeManager;
	
	public AdminView(RootPaneContainer root, MiddleFactory mf, int x, int y) {
		try {
			this.employeeManager = mf.makeEmployeeManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Container cp         = root.getContentPane();    // Content Pane
	    Container rootWindow = (Container) root;         // Root Window
	    cp.setLayout(null);                             // No layout manager
	    rootWindow.setSize( W, H );                     // Size of Window
	    rootWindow.setLocation( x, y );
	    
	    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is
	    
	    rootWindow.setVisible( true );                  // Make visible
	}

}
