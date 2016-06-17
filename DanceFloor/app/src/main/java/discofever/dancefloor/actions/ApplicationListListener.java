package discofever.dancefloor.actions;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import discofever.dancefloor.helpers.ApplicationHelper;
import discofever.dancefloor.model.AppDescriptor;
import discofever.dancefloor.model.AppDetails;
import discofever.dancefloor.model.LauncherModel;

/**
 * Created by TimY on 26/01/2016.
 */
public class ApplicationListListener
{
    private SharedData m_data;
    public AdapterView.OnItemClickListener clickListener;
    public AdapterView.OnItemLongClickListener longClickListener;

    public ApplicationListListener(Activity parent, LauncherModel model, List<AppDescriptor> appList)
    {
        m_data = new SharedData();
        m_data.appList = appList;
        m_data.parentActivity = parent;
        m_data.model = model;

        clickListener = new ClickListener(m_data);
        longClickListener = new LongClickListener(m_data);
    }

    // CLASSES
    public class SharedData
    {
        public List<AppDescriptor>  appList;
        public Activity             parentActivity;
        public LauncherModel        model;
    }

    public class ClickListener implements AdapterView.OnItemClickListener
    {
        private SharedData m_data;

        public ClickListener(SharedData data)
        {
            m_data = data;
        }

        @Override
        public void onItemClick(AdapterView<?> av, View v, int pos,long id)
        {
            ApplicationHelper.launchApplication(m_data.parentActivity, m_data.model, m_data.appList.get(pos));
        }
    }

    public class LongClickListener implements AdapterView.OnItemLongClickListener
    {
        private SharedData m_data;

        public LongClickListener(SharedData data)
        {
            m_data = data;
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
            return true;
        }
    }
}
