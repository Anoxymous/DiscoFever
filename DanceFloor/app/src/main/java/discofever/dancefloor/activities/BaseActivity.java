package discofever.dancefloor.activities;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;

/**
 * Created by TimY on 7/02/2016.
 */
public class BaseActivity extends Activity
{
    //runs without a timer by reposting this handler at the end of the runnable
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            onUpdate();
            timerHandler.postDelayed(this, 500);
        }
    };

    private SensorEventListener mEventListener = new SensorEventListener()
    {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            // Handle the events for which we registered
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
                    break;

                case Sensor.TYPE_MAGNETIC_FIELD:
                    System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
                    break;
            }
        }
    };

    private float[] mValuesMagnet      = new float[3];
    private float[] mValuesAccel       = new float[3];
    private float[] mValuesOrientation = new float[3];
    private float[] mRotationMatrix    = new float[9];

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        timerHandler.postDelayed(timerRunnable, 0);

        SensorManager sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        setListeners(sensorManager, mEventListener);
    }


    protected void onUpdate()
    {
        // Override this class
    }

    @Override
    @CallSuper
    public void onPause()
    {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    @CallSuper
    public void onResume()
    {
        super.onResume();
        timerHandler.postDelayed(timerRunnable, 0);
    }


    // Register the event listener and sensor type.
    public void setListeners(SensorManager sensorManager, SensorEventListener mEventListener)
    {
        sensorManager.registerListener(mEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(mEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public int getRotation()
    {
        SensorManager.getRotationMatrix(mRotationMatrix, null, mValuesAccel, mValuesMagnet);
        SensorManager.getOrientation(mRotationMatrix, mValuesOrientation);

        double r1 = Math.toDegrees(mValuesOrientation[0]) + 180;
        double r2 = Math.toDegrees(mValuesOrientation[1]) + 180;
        double r3 = Math.toDegrees(mValuesOrientation[2]) + 180;

        return (int)r1;
    }

}
