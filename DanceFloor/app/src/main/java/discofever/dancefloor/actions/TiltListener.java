package discofever.dancefloor.actions;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by TimY on 6/07/2016.
 */
public class TiltListener implements SensorEventListener
{
    public float tiltX;
    public float tiltY;

    public void onSensorChanged(SensorEvent event)
    {
        //set ball speed based on phone tilt (ignore Z axis)
        tiltX = -event.values[0];
        tiltY = event.values[1];
        //timer event will redraw ball
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //ignore this event
    }
}

