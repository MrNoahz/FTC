package org.firstinspires.ftc.robotcontroller.internal.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

/**
 * Created by noah on 12/6/16.
 */

public class Accelerometer implements SensorEventListener {

    private SensorManager sensorManager;
    private long lastUpdate;

    private float x;
    private float y;
    private float z;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void getAccelerometer(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelarationSqrt = (float)
                                 ((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2))
                                 / Math.pow(SensorManager.GRAVITY_EARTH, 2));
        long actualTime = event.timestamp;

        if(accelarationSqrt >= 2) {
            if(actualTime - lastUpdate < 200) {
                return;
            }

            lastUpdate = actualTime;
        }

        setX(x);
        setY(y);
        setZ(z);
    }

    public void registerListener(FtcRobotControllerActivity ftcRobotControllerActivity) {

        sensorManager = (SensorManager) ftcRobotControllerActivity.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        lastUpdate = System.currentTimeMillis();

    }

    public void unregisterListener(FtcRobotControllerActivity ftcRobotControllerActivity) {
        sensorManager.unregisterListener(this);
    }
}
