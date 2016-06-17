package discofever.dancefloor.helpers;

import android.app.Activity;
import android.view.Surface;
import android.widget.RelativeLayout;

import discofever.dancefloor.R;
import discofever.dancefloor.model.AppBox;
import discofever.dancefloor.model.AppDescriptor;
import discofever.dancefloor.model.AppDetails;
import discofever.dancefloor.model.LauncherModel;
import discofever.dancefloor.model.LauncherTypes;

/**
 * Created by TimY on 6/02/2016.
 */
public class LayoutHelper
{
    public static void layoutBox(Activity activity, LauncherModel model, RelativeLayout layout, AppBox boxDetails, double x, double y, double w, double h, int rotation)
    {
        if (layout != null && boxDetails != null)
        {
            double gridX = boxDetails.getGridX();
            double gridY = boxDetails.getGridY();

            if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
            {
                double tmp = gridX;
                gridX = gridY;
                gridY = tmp;
            }

            double sizeX = w / gridX;
            double sizeY = h / gridY;

            double sizeSquare = Math.min(sizeX, sizeY);

            if (sizeSquare > 0)
            {
                for (int indexX = 0; indexX < gridX; indexX++)
                {
                    for (int indexY = 0; indexY < gridY; indexY++)
                    {
                        int indexA = -1;
                        int indexB = -1;

                        if(rotation == Surface.ROTATION_0)
                        {
                            indexA = indexX;
                            indexB = indexY;
                        }
                        else if(rotation == Surface.ROTATION_90)
                        {
                            indexA = (int)gridY - (indexY + 1);
                            indexB = indexX;
                        }
                        else if(rotation == Surface.ROTATION_180)
                        {
                            indexA = (int)gridX - (indexX + 1);
                            indexB = (int)gridY - (indexY + 1);
                        }
                        else if(rotation == Surface.ROTATION_270)
                        {
                            indexA = indexY;
                            indexB = (int)gridX - (indexX + 1);
                        }

                        int itemSize = boxDetails.getSizeAt(indexA, indexB);

                        if( itemSize > 0 )
                        {
                            double posX = x + (sizeX * indexX) + (sizeX * 0.5 * itemSize) - (sizeSquare * 0.5 * itemSize);
                            double posY = y + (sizeY * indexY) + (sizeY * 0.5 * itemSize) - (sizeSquare * 0.5 * itemSize);
                            double posS = itemSize * sizeSquare;

                            LauncherTypes.E_AppBoxType itemType = boxDetails.getTypeAt(indexA, indexB);
                            switch (itemType)
                            {
                                case ITEM_APP:
                                    AppDescriptor app = boxDetails.getAppAt(indexA, indexB);
                                    ApplicationHelper.addApplicationButtonToLayout(activity, model, app, layout, posX, posY, posS, posS, rotation);
                                    break;

                                case ITEM_EMPTY:
                                default:
                                    // do nothing
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
}
