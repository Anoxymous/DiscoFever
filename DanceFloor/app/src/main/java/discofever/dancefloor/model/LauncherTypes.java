package discofever.dancefloor.model;

import android.content.Intent;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

    public enum E_TextMode
    {
        TEXT_NOT_SHOWN,     // No backdrop
        TEXT_BASIC,         // Glass button
        TEXT_BOXED,         // Glass button
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

    public enum E_IntentType
    {
        INTENT_UNKNOWN,
        INTENT_CATEGORY,
        INTENT_ACTION,
    }

    public enum E_DefaultTypes
    {
        LAUNCH_ALARM_CLOCK,
        LAUNCH_BROWSER,
        LAUNCH_CALENDAR,
        LAUNCH_CALCULATOR,
        LAUNCH_CAMERA,
        LAUNCH_CONTACTS,
        LAUNCH_DIAL_PAD,
        LAUNCH_EMAIL,
        LAUNCH_GALLERY,
        LAUNCH_MAPS,
        LAUNCH_MARKET,
        LAUNCH_MESSAGING,
        LAUNCH_MUSIC,
    }

    public static String getDefaultTypeStr(E_DefaultTypes type)
    {
        String typeStr = "n/a";
        switch (type)
        {
            case LAUNCH_ALARM_CLOCK:
                typeStr = "Clock";
                break;

            case LAUNCH_BROWSER:
                typeStr = "Browser";
                break;

            case LAUNCH_CALENDAR:
                typeStr = "Calendar";
                break;

            case LAUNCH_CALCULATOR:
                typeStr = "Calculator";
                break;

            case LAUNCH_CAMERA:
                typeStr = "Camera";
                break;

            case LAUNCH_CONTACTS:
                typeStr = "Contacts";
                break;

            case LAUNCH_DIAL_PAD:
                typeStr = "Phone";
                break;

            case LAUNCH_EMAIL:
                typeStr = "E-Mail";
                break;

            case LAUNCH_GALLERY:
                typeStr = "Gallery";
                break;

            case LAUNCH_MAPS:
                typeStr = "Maps";
                break;

            case LAUNCH_MARKET:
                typeStr = "Market";
                break;

            case LAUNCH_MESSAGING:
                typeStr = "Messages";
                break;

            case LAUNCH_MUSIC:
                typeStr = "Music";
                break;
        }
        return typeStr;
    }

    public static E_IntentType getDefaultTypeIntentType(E_DefaultTypes type)
    {
        E_IntentType intentType = E_IntentType.INTENT_UNKNOWN;
        switch (type)
        {
            case LAUNCH_ALARM_CLOCK:
            case LAUNCH_CAMERA:
            case LAUNCH_DIAL_PAD:
                intentType = E_IntentType.INTENT_ACTION;
                break;

            case LAUNCH_BROWSER:
            case LAUNCH_CALENDAR:
            case LAUNCH_CALCULATOR:
            case LAUNCH_CONTACTS:
            case LAUNCH_EMAIL:
            case LAUNCH_GALLERY:
            case LAUNCH_MAPS:
            case LAUNCH_MARKET:
            case LAUNCH_MESSAGING:
            case LAUNCH_MUSIC:
                intentType = E_IntentType.INTENT_CATEGORY;
                break;
        }
        return intentType;
    }

    public static String getDefaultTypeId(E_DefaultTypes type)
    {
        String typeStr = "n/a";
        switch (type)
        {
            case LAUNCH_ALARM_CLOCK:
                typeStr = AlarmClock.ACTION_SHOW_ALARMS;
                break;

            case LAUNCH_BROWSER:
                typeStr = Intent.CATEGORY_APP_BROWSER;
                break;

            case LAUNCH_CALENDAR:
                typeStr = Intent.CATEGORY_APP_CALENDAR;
                break;

            case LAUNCH_CALCULATOR:
                typeStr = Intent.CATEGORY_APP_CALCULATOR;
                break;

            case LAUNCH_CAMERA:
                typeStr = Intent.ACTION_CAMERA_BUTTON;
                break;

            case LAUNCH_CONTACTS:
                typeStr = Intent.CATEGORY_APP_CONTACTS;
                break;

            case LAUNCH_DIAL_PAD:
                typeStr = Intent.ACTION_DIAL;
                break;

            case LAUNCH_EMAIL:
                typeStr = Intent.CATEGORY_APP_EMAIL;
                break;

            case LAUNCH_GALLERY:
                typeStr = Intent.CATEGORY_APP_GALLERY;
                break;

            case LAUNCH_MAPS:
                typeStr = Intent.CATEGORY_APP_MAPS;
                break;

            case LAUNCH_MARKET:
                typeStr = Intent.CATEGORY_APP_MARKET;
                break;

            case LAUNCH_MESSAGING:
                typeStr = Intent.CATEGORY_APP_MESSAGING;
                break;

            case LAUNCH_MUSIC:
                typeStr = Intent.CATEGORY_APP_MUSIC;
                break;
        }
        return typeStr;
    }
}
