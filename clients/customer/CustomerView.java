package clients.customer;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Picture;
import debug.DEBUG;
import events.SimpleDocumentListener;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 * @author  Mike Smith University of Brighton
 * @version 1.0
 */

public class CustomerView implements Observer
{
  class Name                              // Names of buttons
  {
    public static final String CHECK  = "Check";
    public static final String CLEAR  = "Clear";
    public static final String EXPAND = "Expand";
    public static final String HIDE_EXPAND = "Hide";
    public static final String BASKET = "Basket";
    public static final String ADD_BASKET = "Add";
    public static final String REMOVE_BASKET = "Remove";
    public static final String BUY_ONLINE = "Buy online";
  }

  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels
  
  private static final Color VALID_COLOUR = new Color(0x17CC10);
  private static final Color INVALID_COLOUR = Color.red;

  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  @Deprecated
  private final JButton     theBtCheck = new JButton( Name.CHECK );
  private final JButton     theBtClear = new JButton( Name.CLEAR );
  private final JButton expandButton = new JButton(Name.EXPAND);
  // Basket components
  private final JLabel basketLabel = new JLabel(Name.BASKET);
  private final JButton addToBasketButton = new JButton(Name.ADD_BASKET);
  private final JButton removeFromBasketButton = new JButton(Name.REMOVE_BASKET);
  private final JButton buyOnlineButton = new JButton(Name.BUY_ONLINE);
  private final JScrollPane basketScrollPane = new JScrollPane();
  private final JTextArea basketDisplay = new JTextArea();

  private Picture thePicture = new Picture(80,80);
  private StockReader theStock   = null;
  private CustomerController cont= null;
  
  private boolean expandedView = false;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  
  public CustomerView(CustomerModel model, RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock  = mf.makeStockReader();             // Database Access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    JFrame rootWindow = (JFrame) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    theBtCheck.setBounds( 16, 10 + 60*0, 80, 40 );    // Check button
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText() ) );
    cp.add( theBtCheck );                           //  Add to canvas

    theBtClear.setBounds( 16, 10 + 60*1, 80, 40 );    // Clear button
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add( theBtClear );                           //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        //  Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 270, 40 );         // Product no area
    theInput.setText("");
    theInput.getDocument().addDocumentListener(new SimpleDocumentListener() {
		@Override
		public void onChange() {
			String text = theInput.getText();
			if(text.isEmpty()) {
				cont.doClear();
			} else {
				cont.processCheck(text);
			}
		}
	});
    cp.add( theInput );                             //  Add to canvas
    
    theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    theOutput.setEditable(false);
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea

    thePicture.setBounds( 16, 10 + 60*2, 80, 80 );   // Picture area
    cp.add( thePicture );                           //  Add to canvas
    thePicture.clear();
    
    expandButton.setBounds(16, 40 + 60 * 3, 80, 40);
    expandButton.addActionListener((e) -> {
    	boolean expanded = !expandedView;
    	toggleExpandedView(rootWindow, expanded);
    	expandButton.setText(expanded ? Name.HIDE_EXPAND : Name.EXPAND);
    });
    cp.add(expandButton);
    
    // Basket components
    basketLabel.setBounds(16, 270, 80, 20);
    cp.add(basketLabel);
    
    addToBasketButton.setBounds(16, 290, 80, 40);
    addToBasketButton.setVisible(false);
    addToBasketButton.addActionListener(e -> cont.addToBasket());
    cp.add(addToBasketButton);
    
    removeFromBasketButton.setBounds(16, 290 + 60 * 1, 80, 40);
    removeFromBasketButton.setVisible(false);
    removeFromBasketButton.setMargin(new Insets(0, 0, 0, 0));
    removeFromBasketButton.addActionListener(e -> cont.removeFromBasket());
    cp.add(removeFromBasketButton);
    
    buyOnlineButton.setBounds(16, 290 + 60 * 2, 80, 40);
    buyOnlineButton.setVisible(false);
    buyOnlineButton.setMargin(new Insets(0, 0, 0, 0));
    buyOnlineButton.addActionListener(e -> cont.buyOnline());
    cp.add(buyOnlineButton);
    
    basketScrollPane.setBounds(110, 290, 270, 160);
    basketDisplay.setEditable(false);
    cp.add(basketScrollPane);
    basketScrollPane.getViewport().add(basketDisplay);
    
    rootWindow.setVisible( true );                  // Make visible);
    theInput.requestFocus();                        // Focus is here
    
    // Setup listeners
    model.setBasketChangeListener(basket -> updateBasket(basket));
    model.setValidProductCodeListener(b -> {
    	theInput.setForeground(b ? VALID_COLOUR : INVALID_COLOUR);
    });
  }

   /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CustomerController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
   
  public void update( Observable modelC, Object arg )
  {
    CustomerModel model  = (CustomerModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    ImageIcon image = model.getPicture();  // Image of product
    if ( image == null )
    {
      thePicture.clear();                  // Clear picture
    } else {
      thePicture.set( image );             // Display picture
    }
    theOutput.setText(model.getProduct() == null ? "" : model.getProduct().getDescription() );
    theInput.requestFocus();               // Focus is here
  }
  
  private void updateBasket(Basket basket) {
	  basketDisplay.setText(basket.getDetails());
  }
  
  private void toggleExpandedView(JFrame window, boolean expanded) {
	  this.expandedView = expanded;
	  
	  window.setPreferredSize(new Dimension(W, expanded ? H * 2 : H));
	  window.pack(); // forces the window to resize
	  
	  basketLabel.setVisible(expanded);
	  addToBasketButton.setVisible(expanded);
	  removeFromBasketButton.setVisible(expanded);
	  buyOnlineButton.setVisible(expanded);
  }

}
