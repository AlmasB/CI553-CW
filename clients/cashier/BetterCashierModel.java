package clients.cashier;

import catalogue.BetterBasket;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderException;

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
