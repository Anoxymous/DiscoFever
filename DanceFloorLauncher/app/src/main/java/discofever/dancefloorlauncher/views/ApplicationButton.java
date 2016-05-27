package discofever.dancefloorlauncher.views;

//System Includes
import android.app.Activity;
import android.app.Application;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import java.lang.Math;

// Project Includes
import discofever.dancefloorlauncher.R;
import discofever.dancefloorlauncher.actions.ApplicationLaunchListener;
import discofever.dancefloorlauncher.actions.Listener;
import discofever.dancefloorlauncher.helpers.ApplicationHelper;
import discofever.dancefloorlauncher.model.AppDescriptor;
import discofever.dancefloorlauncher.model.AppDetails;
import discofever.dancefloorlauncher.model.LauncherModel;

/**
 * Created by TimY on 25/01/2016.
 */
public class ApplicationButton
{
    private Activity        m_activity;
    private Listener        m_listener;
    private AppDescriptor   m_app;
    private LauncherModel   m_model;

    public ApplicationButton(Activity activity, Listener listener, LauncherModel model, AppDescriptor app)
    {
        m_listener = listener;
        m_activity = activity;
        m_app = app;
        m_model = model;
    }

    public View getView(double r)
    {
        View convertView = m_activity.getLayoutInflater().inflate(R.layout.application_button, null);

        BaseLayout mainLayout = (BaseLayout)convertView.findViewById(R.id.main_layout);
        mainLayout.m_rotation = (int)r;

        ImageView appIcon = (ImageView)convertView.findViewById(R.id.icon_image);
        Drawable iconPic = ApplicationHelper.getIcon(m_model, m_app);
        appIcon.setImageDrawable(iconPic);

        TextView appLabel = (TextView)convertView.findViewById(R.id.app_text);
        appLabel.setText(m_app.launchLabel);

        ImageView iconRim = (ImageView)convertView.findViewById(R.id.button_rim_image);
        ImageView iconBG  = (ImageView)convertView.findViewById(R.id.button_bg_image);

        switch (m_app.iconMode)
        {
            case BASIC_ICON:
                iconRim.setVisibility(View.INVISIBLE);
                iconBG.setVisibility(View.INVISIBLE);
                break;

            case GLASS_ICON:
                iconRim.setVisibility(View.VISIBLE);
                iconBG.setVisibility(View.VISIBLE);
                break;
        }

        Drawable iconBGImage = null;
        switch (m_app.iconBG)
        {
            case ICON_NONE:
                break;

            case ICON_RED:
                iconBGImage = m_activity.getResources().getDrawable(R.drawable.btn_red);
                break;

            case ICON_GREEN:
                iconBGImage = m_activity.getResources().getDrawable(R.drawable.btn_green);
                break;

            case ICON_BLUE:
                iconBGImage = m_activity.getResources().getDrawable(R.drawable.btn_blue);
                break;

            case ICON_YELLOW:
                iconBGImage = m_activity.getResources().getDrawable(R.drawable.btn_yellow);
                break;

            case ICON_TURQUOISE:
                iconBGImage = m_activity.getResources().getDrawable(R.drawable.btn_turquoise);
                break;
        }
        iconBG.setImageDrawable(iconBGImage);

        View clickRegion = (View)convertView.findViewById(R.id.clickable_region);
        clickRegion.setClickable(true);
        clickRegion.setOnClickListener(m_listener.clickListener);
        clickRegion.setOnLongClickListener(m_listener.longClickListener);

        return convertView;
    }
}
