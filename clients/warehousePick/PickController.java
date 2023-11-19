package clients.warehousePick;


/**
 * The BackDoor Controller
 * @author M A Smith (c) June 2014
 */

public class PickController
{
  private PickModel model = null;
  private PickView  view  = null;
  private PickViewFX viewFX = null;
  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public PickController( PickModel model, PickView view )
  {
    this.view  = view;
    this.model = model;
  }
  
  public PickController( PickModel model, PickViewFX viewFX )
  {
    this.viewFX  = viewFX;
    this.model = model;
  }

  /**
   * Picked interaction from view
   */
  public void doPick()
  {
    model.doPick();
  }
  
}

