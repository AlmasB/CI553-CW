package clients;
import java.awt.*;

/**
 * Returns a position for the client window on the screen
 * The clients are assumed to be all the same size 400x300
 * @author Mike Smith University of Brighton
 * @version 1.0
 */
class PosOnScrn
{
  private final static int clientW = 400;
  private final static int clientH = 300;
  
  private static final int maxX;  // Width of screen
  private static final int maxY;  // Height of screen
  
  private static int cX = 0; // Initial window pos on screen
  private static int cY = 0; // Initial window pos on screen
  
  // class initialiser
  //  Will be called (once) when the class is loaded
  static
  {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    maxX = (int) dimension.getWidth();
    maxY = (int) dimension.getHeight();
  }
  
  /**
   * Calculate position of next window
   */
  private static void next()
  {
     if ( cX + 2 * clientW > maxX )
     {
        if ( cY + 2 * clientH < maxY )
        {
            cX = 0; cY += clientH;
        }
     } else {
         cX += clientW;
     }
     // No room on screen
     // All new windows are tiled on top of each other
  }
  
  /**
   * return position for new window on screen
   *  slight misuse of the inbuilt Dimension class
   *  as used to hold an x,y co-ordinate pair
   * @return position for new window
   */
  public static Dimension getPos()
  {
    Dimension pos = new Dimension( cX, cY );
    next();
    return pos;
  }
}
