package clients.shopDisplay;

/**
 * The BackDoor Controller
 * @author M A Smith (c) June 2014
 */

public class DisplayController
{
  private DisplayModel model = null;
  private DisplayView  view  = null;
  private DisplayViewCustomerFX viewFX = null;
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
  
  public DisplayController( DisplayModel model, DisplayViewCustomerFX viewFX )
  {
    this.viewFX  = viewFX;
    this.model = model;
  }
}
