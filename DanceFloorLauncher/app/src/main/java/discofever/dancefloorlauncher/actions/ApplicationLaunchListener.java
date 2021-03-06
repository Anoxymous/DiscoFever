package discofever.dancefloorlauncher.actions;


import android.app.Activity;
import android.view.View;


import discofever.dancefloorlauncher.helpers.ApplicationHelper;
import discofever.dancefloorlauncher.model.AppDescriptor;
import discofever.dancefloorlauncher.model.LauncherModel;

/**
 * Created by TimY on 26/01/2016.
 */
public class ApplicationLaunchListener extends Listener
{
    private AppDescriptor m_app;
    private LauncherModel m_model;
    
    public ApplicationLaunchListener(Activity parentActivity, LauncherModel model, AppDescriptor app)
    {
        super(parentActivity);
        m_app = app;
        m_model = model;
    }

    @Override
    public void click(View v)
    {
        ApplicationHelper.launchApplication(m_parentActivity, m_model, m_app);
    }

    @Override
    public void longClick()
    {

    }

    public void touch()
    {

    }
}
