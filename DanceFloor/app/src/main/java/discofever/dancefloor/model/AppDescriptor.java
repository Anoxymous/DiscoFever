package discofever.dancefloor.model;

/**
 * Created by TimY on 20/05/2016.
 */
public class AppDescriptor implements java.io.Serializable
{
    public LauncherTypes.E_LaunchTypes      launchType;
    public String                           launchId;
    public String                           launchLabel;
    public LauncherTypes.E_IconType         iconType;
    public String                           iconId;
    public LauncherTypes.E_IconMode         iconMode;
    public LauncherTypes.E_IconBackground   iconBG;
    public LauncherTypes.E_TextMode         labelMode;
};

