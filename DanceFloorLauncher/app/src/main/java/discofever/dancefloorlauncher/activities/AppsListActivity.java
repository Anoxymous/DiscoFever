package discofever.dancefloorlauncher;

/**
 * Created by TimY on 24/01/2016.
 */

// System Includes:
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

// Project Includes:
import discofever.dancefloorlauncher.actions.ApplicationListListener;
import discofever.dancefloorlauncher.helpers.ApplicationHelper;
import discofever.dancefloorlauncher.helpers.ModelHelper;
import discofever.dancefloorlauncher.model.AppDescriptor;
import discofever.dancefloorlauncher.model.AppDetails;
import discofever.dancefloorlauncher.model.LauncherModel;

public class AppsListActivity extends Activity
{
    private List<AppDescriptor> m_appList;
    private ListView m_listView;
    LauncherModel m_model;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);

        m_model = ModelHelper.initialiseModel(this);

        loadApps();
        loadListView();
        addListeners();
    }

    private void loadApps()
    {
        Object[] apps = m_model.m_appExecMap.values().toArray();
        for(int i = 0; i < apps.length; i++)
        {
            AppDetails app = (AppDetails)apps[i];
            m_appList.add(app.defaultDescriptor);
        }
    }

    private void loadListView()
    {
        m_listView = (ListView)findViewById(R.id.apps_list);
        ArrayAdapter<AppDescriptor> adapter = new AppDetailsArrayItem(this, m_model, R.layout.list_item, m_appList);
        m_listView.setAdapter(adapter);
    }

    private void addListeners()
    {
        ApplicationListListener listener = new ApplicationListListener(this, m_model, m_appList);

        m_listView.setOnItemClickListener(listener.clickListener);
        m_listView.setOnItemLongClickListener(listener.longClickListener);
//        m_listView.setOnItemSelectedListener(listener.selectListener);
    }
}
