package com.study.sensor_orientation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sm.registerListener(mSensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    SensorEventListener mSensorListener= new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int angle = (int) event.values[0];
            if (angle > 0 && angle < 90) {
                Log.d("sensor_orientation", "东北方向");
            } else if (angle > 90 && angle < 180) {
                Log.d("sensor_orientation", "东南方向");
            } else if (angle > 180 && angle < 270) {
                Log.d("sensor_orientation", "西南方向");
            } else if (angle > 270 && angle < 360) {
                Log.d("sensor_orientation", "西北方向");
            } else if (angle == 0) {
                Log.d("sensor_orientation", "北方向");
            } else if (angle == 90) {
                Log.d("sensor_orientation", "东方向");
            } else if (angle == 180) {
                Log.d("sensor_orientation", "南方向");
            } else if (angle == 270) {
                Log.d("sensor_orientation", "西方向");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(mSensorListener, sensor);
        mSensorListener = null;
    }
}
