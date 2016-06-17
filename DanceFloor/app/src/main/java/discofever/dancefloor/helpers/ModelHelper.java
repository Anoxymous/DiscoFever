package discofever.dancefloor.helpers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import discofever.dancefloor.model.AppBox;
import discofever.dancefloor.model.AppDescriptor;
import discofever.dancefloor.model.AppDetails;
import discofever.dancefloor.model.LauncherModel;
import discofever.dancefloor.model.LauncherTypes;

/**
 * Created by TimY on 31/01/2016.
 */
public class ModelHelper
{
    private static final String C_LAUNCH_MODEL_FILE_NAME = "model";

    public static LauncherModel initialiseModel(ContextWrapper context)
    {

        LauncherModel model = new LauncherModel();
        updateDrawableMap(model);
        ApplicationHelper.updateSystemApplicationList(context, model);
        ApplicationHelper.updateSystemSpecialApplicationList(context, model);

        boolean loaded = loadModelData(context, model);
        if (!loaded)
        {
            createDefaultModel(context, model);
            saveModelData(context, model);
        }

        return model;
    }

    public static void saveModelData(Context context, LauncherModel model)
    {
        String modelData = serialiseModel(model);

        try
        {
            FileOutputStream fos = context.openFileOutput(C_LAUNCH_MODEL_FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            writer.write(modelData);
            writer.newLine();
            writer.close();
            fos.close();

        }
        catch (Exception e)
        {
        }
    }

    public static boolean loadModelData(ContextWrapper context, LauncherModel model)
    {
        boolean success = false;
        try
        {
            FileInputStream fis = context.openFileInput(C_LAUNCH_MODEL_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String modelData = reader.readLine();
            success = deserialiseModel(modelData, model);
            reader.close();
            fis.close();
        }
        catch (Exception e)
        {
        }

        return success;
    }

    private static String serialiseModel(LauncherModel model)
    {
        String modelData = "";

        // serialize the object
        try
        {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(model.m_mainAppBox);
            so.flush();
            modelData = bo.toString();
        }
        catch (Exception e)
        {
            modelData = e.toString();
            // Error occured
        }

        return modelData;
    }

    private static boolean deserialiseModel(String modelData, LauncherModel model)
    {
        boolean success = false;
        // deserialize the object
        try
        {
            byte b[] = modelData.getBytes();
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            model.m_mainAppBox = (AppBox) si.readObject();
            success = true;
        }
        catch (Exception e)
        {
            // Error occured
        }
        return success;
    }

    private static void createDefaultModel(ContextWrapper context, LauncherModel model)
    {
        model.m_mainAppBox = new AppBox();
        model.m_mainAppBox.setGridSize(4, 9);

        String appDialerId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_DIAL_PAD);
        String appContactsId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_CONTACTS);
        String appMsgsId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_MESSAGING);
        String appCalendarId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_CALENDAR);
        String appMusicId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_MUSIC);
        String appCameraId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_CAMERA);
        String appGalleryId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_GALLERY);
        String appAlarmsId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_ALARM_CLOCK);
        String appBrowserId = LauncherTypes.getDefaultTypeId(LauncherTypes.E_DefaultTypes.LAUNCH_BROWSER);


        AppDetails appDialer = model.m_specialExecMap.get(appDialerId);
        if(appDialer != null)
        {
            AppDescriptor app = appDialer.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.GLASS_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_GREEN;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 0, 0, 3);
        }

        AppDetails appContacts = model.m_specialExecMap.get(appContactsId);
        if(appContacts != null)
        {
            AppDescriptor app = appContacts.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.GLASS_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_BLUE;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 0, 3, 3);
        }

        AppDetails appMsgs = model.m_specialExecMap.get(appMsgsId);
        if(appMsgs != null)
        {
            AppDescriptor app = appMsgs.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.GLASS_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_TURQUOISE;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 0, 6, 3);
        }

        AppDetails appCalendar = model.m_specialExecMap.get(appCalendarId);
        if(appCalendar != null)
        {
            AppDescriptor app = appCalendar.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 3, 0, 1);
        }

        AppDetails appMusic = model.m_specialExecMap.get(appMusicId);
        if(appMusic != null)
        {
            AppDescriptor app = appMusic.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 3, 1, 1);
        }

        AppDetails appCamera = model.m_specialExecMap.get(appCameraId);
        if(appCamera != null)
        {
            AppDescriptor app = appCamera.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 3, 2, 1);
        }

        AppDetails appGallery = model.m_specialExecMap.get(appGalleryId);
        if(appGallery != null)
        {
            AppDescriptor app = appGallery.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 3, 3, 1);
        }

        AppDetails appAlarms = model.m_specialExecMap.get(appAlarmsId);
        if(appAlarms != null)
        {
            AppDescriptor app = appAlarms.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 3, 4, 1);
        }

        AppDetails appBrowser = model.m_specialExecMap.get(appBrowserId);
        if(appBrowser != null)
        {
            AppDescriptor app = appBrowser.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.BASIC_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_NONE;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 3, 5, 1);
        }

        AppDetails appLister = model.m_specialExecMap.get("APP_LIST");
        if(appLister != null)
        {
            AppDescriptor app = appLister.defaultDescriptor;
            app.iconMode = LauncherTypes.E_IconMode.GLASS_ICON;
            app.iconBG = LauncherTypes.E_IconBackground.ICON_GREEN;
            app.labelMode = LauncherTypes.E_TextMode.TEXT_NOT_SHOWN;
            model.m_mainAppBox.addAppToBox(app, 3, 7, 1);
        }
    }

    private static void updateDrawableMap(LauncherModel model)
    {
        HashMap<String, Drawable> resMap = new HashMap<String, Drawable>();
//        resMap.put("APP_LIST", getAppLister(context));
        model.m_resourceMap = resMap;
    }
}