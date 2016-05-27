package discofever.dancefloorlauncher.model;

/**
 * Created by TimY on 31/01/2016.
 */
public class LauncherTypes
{
    public enum E_IconMode
    {
        BASIC_ICON,         // No backdrop
        GLASS_ICON,         // Glass button
    }

    public enum E_IconBackground
    {
        ICON_NONE,          // No Icon BG
        ICON_RED,           // Icon BG is red
        ICON_GREEN,         // Icon BG is green
        ICON_BLUE,
        ICON_TURQUOISE,
        ICON_YELLOW,
    }

    public enum E_AppBoxType
    {
        ITEM_EMPTY,
        ITEM_APP,
        ITEM_WIDGET,
        ITEM_APP_BOX,
    }

    public enum E_LaunchTypes
    {
        LAUNCH_SPECIAL,
        LAUNCH_APPLICATION,
        LAUNCH_WIDGET,
    }

    public enum E_IconType
    {
        ICON_DEFAULT,
        ICON_RESOURCE,
        ICON_FILE,
    }

}
