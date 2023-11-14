package middle;

import java.util.List;

import catalogue.Product;

/**
  * Interface for read/write access to the stock list.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
 
public interface StockReadWriter extends StockReader
{
 /**
   * Customer buys stock,
   * stock level is thus decremented by amount bought.
   * @param pNum Product number
   * @param amount Quantity of product
   * @return StockNumber, Description, Price, Quantity
   * @throws middle.StockException if issue
   */
  boolean buyStock(String pNum, int amount) throws StockException;
  
  /**
   * Tries to buy all of the stock in the list. Will buy as many products as possible and ignore
   * products that are out of stock. 
   * @param list The list of products to buy
   * @return A list containing all the products that could not be bought.
   * 	 	 If the list is empty, all products have been bought
   * @throws StockException If an error occurred while buying stock
   */
  List<Product> buyAllStock(List<Product> list) throws StockException;

  /**
   * Adds stock (Restocks) to store.
   * @param pNum Product number
   * @param amount Quantity of product
   * @throws middle.StockException if issue
   */
  void addStock(String pNum, int amount) throws StockException;
  
  /**
   * Modifies Stock details for a given product number.
   * Information modified: Description, Price
   * @param detail Replace with this version of product
   * @throws middle.StockException if issue
   */
  void modifyStock(Product detail) throws StockException;

}
