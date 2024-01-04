package clients.cashier;

import catalogue.BetterBasket;
import clients.cashier.CashierModel.State;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderException;
import middle.StockException;

/**
 * Implements the Model of the cashier client with BetterBasket
 * 
 * @author Zayan Rehman University of Brighton
 * @version 1.0
 */
public class BetterCashierModel extends CashierModel {

    private BetterBasket theBetterBasket = null; // Bought items

    public BetterCashierModel(MiddleFactory mf) {
        super(mf);
        {
            try {
                theStock = mf.makeStockReadWriter(); // Database access
                theOrder = mf.makeOrderProcessing(); // Process order
            } catch (Exception e) {
                DEBUG.error("CashierModel.constructor\n%s", e.getMessage());
            }
            theState = State.process;
        } // Current state
    }
    /**
     * Buy the product
     */
    public void doBuy()
    {
      String theAction = "";
      int    amount  = 1;                         //  & quantity
      try
      {
        if ( theState != State.checked )          // Not checked
        {                                         //  with customer
          theAction = "Check if OK with customer first";
        } else {
          boolean stockBought =                   // Buy
            theStock.buyStock(                    //  however
              theProduct.getProductNum(),         //  may fail              
              theProduct.getQuantity() );         //
          if ( stockBought )                      // Stock bought
          {                                       // T
            makeBetterBasketIfReq();                    //  new Basket ?
            theBetterBasket.add( theProduct );          //  Add to bought
            theAction = "Purchased " +            //    details
                    theProduct.getDescription();  //
          } else {                                // F
            theAction = "!!! Not in stock";       //  Now no stock
          }
        }
      } catch( StockException e )
      {
        DEBUG.error( "%s\n%s", 
              "BetterCashierModel.doBuy", e.getMessage() );
        theAction = e.getMessage();
      }
      theState = State.process;                   // All Done
      setChanged(); 
      notifyObservers(theAction);
    }
    
    /**
     * Customer pays for the contents of the basket
     */
    public void doBought()
    {
      String theAction = "";
      int    amount  = 1;                       //  & quantity
      try
      {
        if ( theBetterBasket != null &&
             theBetterBasket.size() >= 1 )            // items > 1
        {                                       // T
          theOrder.newOrder( theBetterBasket );       //  Process order
          theBetterBasket = null;                     //  reset
        }                                       //
        theAction = "Next customer";            // New Customer
        theState = State.process;               // All Done
        theBetterBasket = null;
      } catch( OrderException e )
      {
        DEBUG.error( "%s\n%s", 
              "CashierModel.doCancel", e.getMessage() );
        theAction = e.getMessage();
      }
      theBetterBasket = null;
      setChanged(); notifyObservers(theAction); // Notify
    }

    /**
     * ask for update of view called at start of day
     * or after system reset
     */
    public void askForUpdate()
    {
      setChanged(); notifyObservers("Welcome");
    }
    /**
     * Get the BetterBasket of products
     * 
     * @return BetterBasket
     */
    public BetterBasket getBetterBasket() {
        return theBetterBasket;
    }

    protected void makeBetterBasketIfReq() {
        if (theBetterBasket == null) {
            try {
                int uon = theOrder.uniqueNumber(); // Unique order num.
                theBetterBasket = makeBetterBasket(); // basket list
                theBetterBasket.setOrderNum(uon); // Add an order number
            } catch (OrderException e) {
                DEBUG.error("Comms failure\n" + "CashierModel.makeBasket()\n%s", e.getMessage());
            }
        }
    }

    /**
     * Return an instance of a new BetterBasket
     * 
     * @return an instance of a new BetterBasket
     */
    private BetterBasket makeBetterBasket() {
        return new BetterBasket();
    }

    /**
     * Override the makeBasket method to return a BetterBasket
     * 
     * @return a BetterBasket instance
     */
    @Override
    protected BetterBasket makeBasket() {
        makeBetterBasketIfReq();
        return theBetterBasket;
    }
}
