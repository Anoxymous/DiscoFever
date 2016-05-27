package discofever.dancefloorlauncher.helpers;

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

import discofever.dancefloorlauncher.model.AppBox;
import discofever.dancefloorlauncher.model.AppDescriptor;
import discofever.dancefloorlauncher.model.AppDetails;
import discofever.dancefloorlauncher.model.LauncherModel;
import discofever.dancefloorlauncher.model.LauncherTypes;

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
        model.m_mainAppBox.setGridSize(3, 6);

        AppDescriptor appLister1 = model.m_specialExecMap.get("APP_LIST").defaultDescriptor;
        model.m_mainAppBox.addAppToBox(appLister1, 0, 0, 1);

        AppDescriptor appLister2 = model.m_specialExecMap.get("APP_LIST").defaultDescriptor;
        appLister2.iconMode = LauncherTypes.E_IconMode.GLASS_ICON;
        model.m_mainAppBox.addAppToBox(appLister2, 1, 1, 1);

        AppDescriptor appLister3 = model.m_specialExecMap.get("APP_LIST").defaultDescriptor;
        appLister3.iconMode = LauncherTypes.E_IconMode.GLASS_ICON;
        appLister3.iconBG = LauncherTypes.E_IconBackground.ICON_BLUE;
        model.m_mainAppBox.addAppToBox(appLister3, 2, 2, 1);

        AppDescriptor appLister4 = model.m_specialExecMap.get("APP_LIST").defaultDescriptor;
        appLister4.iconMode = LauncherTypes.E_IconMode.GLASS_ICON;
        appLister4.iconBG = LauncherTypes.E_IconBackground.ICON_RED;
        model.m_mainAppBox.addAppToBox(appLister4, 0, 2, 1);


        AppDescriptor appLister5 = model.m_specialExecMap.get("APP_LIST").defaultDescriptor;
        appLister5.iconMode = LauncherTypes.E_IconMode.GLASS_ICON;
        appLister5.iconBG = LauncherTypes.E_IconBackground.ICON_GREEN;
        model.m_mainAppBox.addAppToBox(appLister5, 1, 3, 1);

    }

    private static void updateDrawableMap(LauncherModel model)
    {
        HashMap<String, Drawable> resMap = new HashMap<String, Drawable>();
//        resMap.put("APP_LIST", getAppLister(context));
        model.m_resourceMap = resMap;
    }
}