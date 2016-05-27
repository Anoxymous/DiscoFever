package discofever.dancefloorlauncher.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import discofever.dancefloorlauncher.AppsListActivity;
import discofever.dancefloorlauncher.R;
import discofever.dancefloorlauncher.actions.ApplicationLaunchListener;
import discofever.dancefloorlauncher.model.AppDetails;
import discofever.dancefloorlauncher.model.AppDescriptor;
import discofever.dancefloorlauncher.model.LauncherModel;
import discofever.dancefloorlauncher.model.LauncherTypes;
import discofever.dancefloorlauncher.views.ApplicationButton;

/**
 * Created by TimY on 26/01/2016.
 */
public class ApplicationHelper
{
    public static void updateSystemApplicationList(ContextWrapper context, LauncherModel model)
    {
        List<AppDetails> appList = getSystemApplicationList(context);
        HashMap<String, AppDetails> appMap = new HashMap<String, AppDetails>();

        for(int i = 0; i < appList.size(); i++)
        {
            AppDetails app = appList.get(i);
            appMap.put(app.defaultDescriptor.launchId, app);
        }
        model.m_appExecMap = appMap;
    }

    public static List<AppDetails> getSystemApplicationList(ContextWrapper context)
    {
        List<AppDetails> appList = new ArrayList<AppDetails>();

        PackageManager packageManager = context.getPackageManager();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(i, 0);

        for (ResolveInfo ri : availableActivities)
        {
            AppDescriptor appData = new AppDescriptor();
            appData.launchId = ri.activityInfo.packageName.toString();
            appData.launchType = LauncherTypes.E_LaunchTypes.LAUNCH_APPLICATION;
            appData.launchLabel = ri.loadLabel(packageManager).toString();
            appData.iconType = LauncherTypes.E_IconType.ICON_DEFAULT;
            appData.iconId = "";
            appData.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
            appData.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;

            AppDetails app = new AppDetails();
            app.defaultDescriptor = appData;
            app.systemIcon = ri.activityInfo.loadIcon(packageManager);
            app.launchIntent = packageManager.getLaunchIntentForPackage(appData.launchId);

            appList.add(app);
        }

        return appList;
    }

    public static void updateSystemSpecialApplicationList(ContextWrapper context, LauncherModel model)
    {
        HashMap<String, AppDetails> appMap = new HashMap<String, AppDetails>();

        AppDetails app = getAppLister(context);
        appMap.put(app.defaultDescriptor.launchId, app);
        model.m_specialExecMap = appMap;
    }

    public static AppDetails getAppLister(ContextWrapper context)
    {
        AppDescriptor appData = new AppDescriptor();
        appData.launchId = "APP_LIST";
        appData.launchType = LauncherTypes.E_LaunchTypes.LAUNCH_SPECIAL;
        appData.launchLabel = "App Draw";
        appData.iconType = LauncherTypes.E_IconType.ICON_DEFAULT;
        appData.iconId = "";
        appData.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
        appData.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;

        AppDetails app = new AppDetails();
        app.defaultDescriptor = appData;
        app.systemIcon = context.getResources().getDrawable(R.drawable.icon);
        app.launchIntent = new Intent(context, AppsListActivity.class);

        return app;
    }

    public static void addApplicationButtonToLayout(Activity activity, LauncherModel model, AppDescriptor app, ViewGroup layout, double x, double y, double w, double h, double r)
    {
        if(activity != null && app != null && layout != null)
        {
            ApplicationLaunchListener actions = new ApplicationLaunchListener(activity, model, app);
            ApplicationButton appButton = new ApplicationButton(activity, actions, model, app);

            RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams((int) w, (int) h);
            buttonLayout.leftMargin = (int) x;
            buttonLayout.topMargin = (int) y;
            layout.addView(appButton.getView(r), buttonLayout);
        }
    }

    public static void launchApplication(Activity launcher, LauncherModel model, AppDescriptor appData)
    {
        if(launcher != null && appData != null && model != null)
        {
            AppDetails app = null;
            switch (appData.launchType)
            {
                case LAUNCH_APPLICATION:
                    app = model.m_appExecMap.get(appData.launchId);
                    break;

                case LAUNCH_SPECIAL:
                    app = model.m_specialExecMap.get(appData.launchId);
                    break;

                case LAUNCH_WIDGET:
                    app = model.m_widgetExecMap.get(appData.launchId);
                    break;
            }

            if(app != null && app.launchIntent != null)
            {
                launcher.startActivity(app.launchIntent);
            }
        }
    }

    public static Drawable getIcon(LauncherModel model, AppDescriptor app)
    {
        Drawable iconPic = null;

        switch(app.iconType)
        {
            case ICON_DEFAULT:
                iconPic = model.m_appExecMap.get(app.launchId).systemIcon;
                break;

            case ICON_RESOURCE:
                iconPic = model.m_resourceMap.get(app.iconId);
        }

        return iconPic;
    }
}
