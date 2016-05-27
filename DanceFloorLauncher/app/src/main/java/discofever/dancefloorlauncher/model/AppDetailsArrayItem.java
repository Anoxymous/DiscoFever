package discofever.dancefloorlauncher;

//System Includes
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.lang.Math;

// Project Includes
import discofever.dancefloorlauncher.helpers.ApplicationHelper;
import discofever.dancefloorlauncher.model.AppDescriptor;
import discofever.dancefloorlauncher.model.AppDetails;
import discofever.dancefloorlauncher.model.LauncherModel;

/**
 * Created by TimY on 25/01/2016.
 */
public class AppDetailsArrayItem extends ArrayAdapter<AppDescriptor>
{
    private List<AppDescriptor> m_appList;
    private Activity m_activity;
    private LauncherModel m_model;
    double m_scale;

    public AppDetailsArrayItem(Activity activity, LauncherModel model, @LayoutRes int resource, @NonNull List<AppDescriptor> appList)
    {
        super(activity, resource, appList);
        m_appList = appList;
        m_activity = activity;
        m_scale = 4;
        m_model = model;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = m_activity.getLayoutInflater().inflate(R.layout.list_item, null);
        }

        double width = m_activity.getWindow().getDecorView().getWidth();
        double height = m_activity.getWindow().getDecorView().getHeight();
        double sizer = Math.min(width, height);

        ImageView appIcon = (ImageView)convertView.findViewById(R.id.item_app_icon);
        Drawable iconPic = ApplicationHelper.getIcon(m_model, m_appList.get(position));
        appIcon.setImageDrawable(iconPic);
        appIcon.getLayoutParams().height = (int)(sizer / m_scale);
        appIcon.getLayoutParams().width = (int)(sizer / m_scale);

        TextView appLabel = (TextView)convertView.findViewById(R.id.item_app_label);
        appLabel.setText(m_appList.get(position).launchLabel);

        TextView appName = (TextView)convertView.findViewById(R.id.item_app_name);
        appName.setText(m_appList.get(position).launchId);

        return convertView;
    }
}
