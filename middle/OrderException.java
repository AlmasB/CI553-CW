package middle;

/**
  * Exception throw if there is an error in the order processing system.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
 
public class OrderException extends Exception
{
  private static final long serialVersionUID = 2;
  public OrderException( String s )
  {
    super(s);
  }
}
