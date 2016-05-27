package discofever.dancefloorlauncher.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by TimY on 31/01/2016.
 */
public class LauncherModel implements java.io.Serializable
{
    public AppBox                       m_mainAppBox;
    public HashMap<String, Drawable>    m_resourceMap;
    public HashMap<String, AppDetails>  m_appExecMap;
    public HashMap<String, AppDetails>  m_widgetExecMap;
    public HashMap<String, AppDetails>  m_specialExecMap;

    public LauncherModel()
    {
        m_mainAppBox     = new AppBox();
        m_resourceMap    = new HashMap<String, Drawable>();
        m_appExecMap     = new HashMap<String, AppDetails>();
        m_widgetExecMap  = new HashMap<String, AppDetails>();
        m_specialExecMap = new HashMap<String, AppDetails>();
    }
}
