package catalogue;

import java.util.Currency;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;

import util.Pair;

public class BasketDetails {
	
	private static final Locale UK_LOCALE = Locale.UK;
	
	private final Basket basket;
	
	private String description;
	// Product Num -> <Start Index, End Index>
	private HashMap<String, Pair<Integer, Integer>> productToIndexesMap = new HashMap<>();
	
	public BasketDetails(Basket basket) {
		this.basket = basket;
		createDetails();
	}
	
	/**
	   * Creates a description of the products in the basket suitable for printing.
	   * @return a string description of the basket products
	   */
	public void createDetails() {
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder, UK_LOCALE);
		String poundSign = Currency.getInstance(UK_LOCALE).getSymbol();
		
		double total = 0.0D;
		
		if(basket.getOrderNum() != 0) 
			formatter.format("Order number: %03d\n", basket.getOrderNum());
		
		for(Product product : basket) {
			int startIndex = builder.length();
			
			int number = product.getQuantity();
	        formatter.format("%-7s",       product.getProductNum() );
	        formatter.format("%-14.14s ",  product.getDescription() );
	        formatter.format("(%3d) ",     number );
	        formatter.format("%s%7.2f",    poundSign, product.getPrice() * number );
	        formatter.format("\n");
	        total += product.getPrice() * number;
	        
	        int endIndex = builder.length();
	        
	        productToIndexesMap.put(product.getProductNum(), new Pair<>(startIndex, endIndex));
		}
		
		formatter.format("----------------------------\n");
		formatter.format("Total                       ");
		formatter.format("%s%7.2f\n",    poundSign, total );
		formatter.close();
		
		this.description = builder.toString();
	}
	
	public String getDescription() {
		return description;
	}
	
	public Pair<Integer, Integer> getProductIndexes(Product product) {
		return productToIndexesMap.get(product.getProductNum());
	}
}
