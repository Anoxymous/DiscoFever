package discofever.dancefloor.model;

import java.lang.Math;
import java.io.Serializable;

/**
 * Created by TimY on 5/02/2016.
 */
public class AppBox implements java.io.Serializable
{
    private int      m_grid_x;
    private int      m_grid_y;
    private Item[][] m_items;

    public AppBox()
    {
        this(3, 3);
    }

    public AppBox(int x, int y)
    {
        if(x > 0 && y > 0)
        {
            m_grid_x = x;
            m_grid_y = y;
        }
        else
        {
            m_grid_x = 3;
            m_grid_y = 3;
        }
        m_items = new Item[m_grid_x][m_grid_y];
    }

    public void setGridSize(int x, int y)
    {
        if(x > 0 && y > 0)
        {
            Item[][] newItems = new Item[x][y];
            for (int i = 0; i < x && i < m_grid_x; i++)
            {
                for (int j = 0; j < y && j < m_grid_y; j++)
                {
                    Item currentItem = m_items[i][j];
                    if (currentItem != null)
                    {
                        newItems[i][j] = currentItem;
                        int largestSizeX = x - i;
                        int largestSizeY = y - j;
                        int largestSize = Math.min(largestSizeX, largestSizeY);
                        if (currentItem.itemSize > largestSize)
                        {
                            currentItem.itemSize = largestSize;
                        }
                    }
                }
            }
            m_grid_x = x;
            m_grid_y = y;
            m_items = newItems;
        }
    }

    public int getGridX()
    {
        return m_grid_x;
    }

    public int getGridY()
    {
        return m_grid_y;
    }

    public int getSizeAt(int x, int y)
    {
        int size = 0;
        Item currentItem = getItemAt(x, y);
        if(currentItem != null)
        {
            size = currentItem.itemSize;
        }
        return size;
    }

    public LauncherTypes.E_AppBoxType getTypeAt(int x, int y)
    {
        LauncherTypes.E_AppBoxType type = LauncherTypes.E_AppBoxType.ITEM_EMPTY;
        Item currentItem = getItemAt(x, y);
        if(currentItem != null)
        {
            type = currentItem.itemType;
        }
        return type;
    }

    public AppDescriptor getAppAt(int x, int y)
    {
        AppDescriptor app = null;
        Item currentItem = getItemAt(x, y);
        if(currentItem != null)
        {
            app = currentItem.itemAppD;
        }
        return app;
    }

    public AppBox getAppBoxAt(int x, int y)
    {
        AppBox appBox = null;
        Item currentItem = getItemAt(x, y);
        if(currentItem != null)
        {
            appBox = currentItem.itemAppBox;
        }
        return appBox;
    }

    public boolean addAppToBox(AppDescriptor appDetails, int x, int y, int s)
    {
        boolean added = false;

        // TODO: check for out of bounds or existing items
        Item item = new Item();
        item.itemType = LauncherTypes.E_AppBoxType.ITEM_APP;
        item.itemSize = s;
        item.itemAppD = appDetails;
        m_items[x][y] = item;
        added = true;

        return added;
    }

    private Item getItemAt(int x, int y)
    {
        Item currentItem = null;
        if( x >= 0 && y >= 0 && x < m_grid_x && y < m_grid_y)
        {
            currentItem = m_items[x][y];
        }
        return currentItem;
    }

    public class Item implements java.io.Serializable
    {
        public LauncherTypes.E_AppBoxType   itemType;
        public int                          itemSize;
        public AppDescriptor                itemAppD;
//        public AppDetails                   itemApp;
        public AppBox                       itemAppBox;
    }

}
