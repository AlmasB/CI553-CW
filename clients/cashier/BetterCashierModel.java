package clients.cashier;

import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderException;
import catalogue.Basket;
import catalogue.BetterBasket;
import catalogue.Product;

public class BetterCashierModel extends CashierModel {

	public BetterCashierModel(MiddleFactory mf) {
		super(mf);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BetterBasket makeBasket() {
		return new BetterBasket();
	}
}
