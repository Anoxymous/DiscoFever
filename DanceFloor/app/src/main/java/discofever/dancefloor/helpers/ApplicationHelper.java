package discofever.dancefloor.helpers;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import discofever.dancefloor.activities.AppsListActivity;
import discofever.dancefloor.R;
import discofever.dancefloor.actions.ApplicationLaunchListener;
import discofever.dancefloor.model.AppDetails;
import discofever.dancefloor.model.AppDescriptor;
import discofever.dancefloor.model.LauncherModel;
import discofever.dancefloor.model.LauncherTypes;
import discofever.dancefloor.views.ApplicationButton;

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
            AppDetails app = getAppDetailsFromResolveInfo(packageManager, ri);
            appList.add(app);
        }

        return appList;
    }

    public static void updateSystemSpecialApplicationList(ContextWrapper context, LauncherModel model)
    {
        HashMap<String, AppDetails> appMap = new HashMap<String, AppDetails>();

        AppDetails app;

        //App List
        app = getAppLister(context);
        appMap.put(app.defaultDescriptor.launchId, app);

        // Default Apps
        for(LauncherTypes.E_DefaultTypes appType : LauncherTypes.E_DefaultTypes.values())
        {
            app = getDefaultAppLauncher(context, appType);
            if(app != null)
            {
                appMap.put(LauncherTypes.getDefaultTypeId(appType), app);
            }
        }

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
        appData.labelMode = LauncherTypes.E_TextMode.TEXT_BASIC;

        AppDetails app = new AppDetails();
        app.defaultDescriptor = appData;
        app.systemIcon = context.getResources().getDrawable(R.drawable.icon);
        app.launchIntent = new Intent(context, AppsListActivity.class);

        return app;
    }

    public static AppDetails getDefaultAppLauncher(ContextWrapper context, LauncherTypes.E_DefaultTypes type)
    {
        AppDetails app = null;

        String command = LauncherTypes.getDefaultTypeId(type);
        PackageManager packageManager = context.getPackageManager();
        LauncherTypes.E_IntentType intentType = LauncherTypes.getDefaultTypeIntentType(type);

        Intent intent = null;

        switch (intentType)
        {
            case INTENT_ACTION:
            {
                intent = new Intent(command);
                List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(intent, 0);
                if (availableActivities.size() > 0)
                {
                    ResolveInfo ri = availableActivities.get(0);
                    intent = new Intent(Intent.ACTION_MAIN, null);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    String packageName = ri.activityInfo.packageName;

                    availableActivities = packageManager.queryIntentActivities(intent, 0);
                    for (ResolveInfo ri2 : availableActivities)
                    {
                        String pn2 = ri2.activityInfo.packageName;
                        if(pn2.equals(packageName))
                        {
                            app = getAppDetailsFromResolveInfo(packageManager, ri2);
                            app.defaultDescriptor.launchLabel = LauncherTypes.getDefaultTypeStr(type);
                            break;
                        }
                    }
                }
            }
            break;

            case INTENT_CATEGORY:
            {
                intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(command);

                List<ResolveInfo> availableActivities = packageManager.queryIntentActivities(intent, 0);
                if (availableActivities.size() > 0) {
                    ResolveInfo ri = availableActivities.get(0);
                    app = getAppDetailsFromResolveInfo(packageManager, ri);
                    app.defaultDescriptor.launchLabel = LauncherTypes.getDefaultTypeStr(type);
                }
            }
            break;

            default:
                break;
        }


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
            AppDetails app = getAppDetails(model, appData);

            if(app != null && app.launchIntent != null)
            {
                launcher.startActivity(app.launchIntent);
            }
        }
    }

    public static Drawable getIcon(LauncherModel model, AppDescriptor appData)
    {
        Drawable iconPic = null;

        switch(appData.iconType)
        {
            case ICON_DEFAULT:
                AppDetails app = getAppDetails(model, appData);
                if(app != null)
                {
                    iconPic = app.systemIcon;
                }
                break;

            case ICON_RESOURCE:
                iconPic = model.m_resourceMap.get(appData.iconId);
        }

        return iconPic;
    }

    public static AppDetails getAppDetails(LauncherModel model, AppDescriptor appData)
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
        return app;
    }

    public static AppDetails getAppDetailsFromResolveInfo(PackageManager packageManager, ResolveInfo ri)
    {
        AppDescriptor appData = new AppDescriptor();
        appData.launchId = ri.activityInfo.packageName + ":" + ri.activityInfo.name;
        appData.launchType = LauncherTypes.E_LaunchTypes.LAUNCH_APPLICATION;
        appData.launchLabel = ri.loadLabel(packageManager).toString();
        appData.iconType = LauncherTypes.E_IconType.ICON_DEFAULT;
        appData.iconId = "";
        appData.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
        appData.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;
        appData.labelMode = LauncherTypes.E_TextMode.TEXT_BASIC;

        AppDetails app = new AppDetails();
        app.defaultDescriptor = appData;
        app.systemIcon = ri.activityInfo.loadIcon(packageManager);
        app.launchIntent = Intent.makeMainActivity(new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name));
        return app;
    }

    public static List<String> getDefaultTypePriorityList(LauncherTypes.E_DefaultTypes type)
    {
        List<String> activities = new ArrayList<String>();

        switch (type)
        {
            case LAUNCH_ALARM_CLOCK:
                activities.add("");
                break;

            case LAUNCH_BROWSER:
                activities.add("com.sec.android.app.sbrowser.SBrowserMainActivity");
                activities.add("org.mozilla.firefox.App");
                break;

            case LAUNCH_CALENDAR:
                activities.add("com.android.calendar.AllInOneActivity");
                break;

            case LAUNCH_CALCULATOR:
                activities.add("com.sec.android.app.popupcalculator.Calculator");
                break;

            case LAUNCH_CAMERA:
                activities.add("");
                break;

            case LAUNCH_CONTACTS:
                activities.add("comm.android.contacts.activities.PeopleActivity");
                break;

            case LAUNCH_DIAL_PAD:
                activities.add("comm.android.dialer.DialtactsActivity");
                break;

            case LAUNCH_EMAIL:
                activities.add("");
                break;

            case LAUNCH_GALLERY:
                activities.add("com.sec.android.gallery3d.app.GalleryActivity");
                break;

            case LAUNCH_MAPS:
                activities.add("com.here.app.OneAppLauncherAlias");
                break;

            case LAUNCH_MARKET:
                activities.add("com.android.vending.AssetBrowserActivity");
                break;

            case LAUNCH_MESSAGING:
                activities.add("com.android.mms.ui.ConversationComposer");
                break;

            case LAUNCH_MUSIC:
                activities.add("");
                break;
        }
        return activities;
    }
}

