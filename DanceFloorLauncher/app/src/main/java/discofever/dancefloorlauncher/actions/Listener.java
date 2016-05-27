package discofever.dancefloorlauncher.actions;

import android.app.Activity;
import android.view.View;

/**
 * Created by TimY on 26/01/2016.
 */
public class Listener
{
    public View.OnClickListener     clickListener;
    public View.OnLongClickListener longClickListener;
    protected Activity m_parentActivity;

    public Listener(Activity parentActivity)
    {
        m_parentActivity = parentActivity;
        clickListener = new ClickListener(this);
        longClickListener = new LongClickListener(this);
    }

    // Override these function
    public void click(View v) {}
    public void longClick() {}

    public class ClickListener implements View.OnClickListener
    {
        private Listener m_listener;
        public ClickListener(Listener l)
        {
            m_listener = l;
        }
        @Override
        public void onClick(View v)
        {
            m_listener.click(v);
        }
    }

    public class LongClickListener implements View.OnLongClickListener
    {
        private Listener m_listener;
        public LongClickListener(Listener l)
        {
            m_listener = l;
        }
        @Override
        public boolean onLongClick(View v) { m_listener.longClick(); return true; }
    }
}
