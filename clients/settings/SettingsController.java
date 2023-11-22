package clients.settings;

import clients.settings.SettingsModel;
import clients.settings.SettingsView;

/**
 * The Settings Controller
 * @author	Callum Barnett
 */
public class SettingsController
{
  private SettingsModel model = null;
  private SettingsView  view  = null;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public SettingsController( SettingsModel model, SettingsView view )
  {
    this.view  = view;
    this.model = model;
  }
}


