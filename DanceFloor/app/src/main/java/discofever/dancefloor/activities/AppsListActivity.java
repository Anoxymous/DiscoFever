package discofever.dancefloor.activities;

/**
 * Created by TimY on 24/01/2016.
 */

// System Includes:
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

// Project Includes:
import discofever.dancefloor.actions.ApplicationListListener;
import discofever.dancefloor.helpers.ApplicationHelper;
import discofever.dancefloor.helpers.ModelHelper;
import discofever.dancefloor.model.AppDescriptor;
import discofever.dancefloor.model.AppDetails;
import discofever.dancefloor.model.LauncherModel;
import discofever.dancefloor.R;

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
        m_appList = new ArrayList<AppDescriptor>();
        for (Map.Entry<String, AppDetails> entry : m_model.m_appExecMap.entrySet())
        {
            AppDetails app = entry.getValue();
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
