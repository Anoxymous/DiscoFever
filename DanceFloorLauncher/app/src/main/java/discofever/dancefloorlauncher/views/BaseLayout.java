package discofever.dancefloorlauncher.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by TimY on 7/02/2016.
 */
public class BaseLayout extends android.support.percent.PercentRelativeLayout
{
    public int m_rotation;

    public BaseLayout(Context context)
    {
        super(context);
        m_rotation = 0;
    }

    public BaseLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        m_rotation = 0;
    }

    public BaseLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        m_rotation = 0;
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.save();
        canvas.rotate(m_rotation, canvas.getWidth() / 2, canvas.getHeight() / 2);
        super.draw(canvas);
        canvas.restore();
    }
}
