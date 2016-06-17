package discofever.dancefloor.model;

/**
 * Created by TimY on 24/01/2016.
 */
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppDetails implements java.io.Serializable
{
//    public CharSequence                     name;
//    public CharSequence                     label;
    public Intent                           launchIntent;
    public Drawable                         systemIcon;
    public AppDescriptor                    defaultDescriptor;
//    public Drawable                         setIcon;
//    public LauncherTypes.E_IconMode         iconMode;
//    public LauncherTypes.E_IconBackground   iconBG;
}

