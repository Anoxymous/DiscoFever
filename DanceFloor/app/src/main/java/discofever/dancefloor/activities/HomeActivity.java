/**
 * Created by TimY on 24/01/2016.
 */

package discofever.dancefloor.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.RelativeLayout;

import discofever.dancefloor.helpers.LayoutHelper;
import discofever.dancefloor.helpers.ModelHelper;
import discofever.dancefloor.model.LauncherModel;
import discofever.dancefloor.R;

public class HomeActivity extends BaseActivity
{
    LauncherModel   m_model;
    double          m_baseX;
    double          m_baseY;
    int             m_baseR;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        m_baseY = 0;
        m_baseX = 0;
        m_baseR = 0;
        m_model = ModelHelper.initialiseModel(this);
    }

    public void onUpdate()
    {
        Rect rectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);

        int w = rectangle.width();
        int h = rectangle.height();
        int r = getWindowManager().getDefaultDisplay().getRotation();

        if(w != m_baseX || h != m_baseY || r != m_baseR)
        {
            if(w > 10 & h > 10)
            {
                m_baseX = w;
                m_baseY = h;
                m_baseR = r;
                updateDisplay();
            }
        }
    }

    private void updateDisplay()
    {
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
            if (layout != null)
            {
                layout.removeAllViews();
                LayoutHelper.layoutBox(this, m_model, layout, m_model.m_mainAppBox, 0, 0, m_baseX, m_baseY, m_baseR);
            }
    }
}