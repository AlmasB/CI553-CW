package clients.collection;

/**
 * The Collection Controller
 * @author M A Smith (c) June 2014
 */

public class CollectController
{
  private CollectModel model = null;
  private CollectView  view  = null;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public CollectController( CollectModel model, CollectView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Collect interaction from view
   * @param orderNum The order collected
   */
  public void doCollect( String orderNum )
  {
    model.doCollect(orderNum);
  } 
}


