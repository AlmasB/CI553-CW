package clients.shopDisplay;

/**
 * The BackDoor Controller
 * @author M A Smith (c) June 2014
 */

public class DisplayController
{
  private DisplayModel model = null;
  private DisplayView  view  = null;
  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public DisplayController( DisplayModel model, DisplayView view )
  {
    this.view  = view;
    this.model = model;
  }
}
