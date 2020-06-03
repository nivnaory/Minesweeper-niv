package com.afeka.sm.Minesweeper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

interface SensorServiceListener {

    enum ALARM_STATE {
        NOT_ON_POSITION, ON_POSITION
    }

    void alarmStateChanged(ALARM_STATE state, int timeSinceLastPositionChanged);
}

public class SensorsService extends Service implements SensorEventListener {

    private final static String TAG = "SENSOR_SERVICE";
    private final double THRESHOLD = 1;
    private SensorServiceListener.ALARM_STATE currentAlarmState = SensorServiceListener.ALARM_STATE.ON_POSITION;


    // Binder given to clients
    private final IBinder mBinder = new SensorServiceBinder();
    SensorServiceListener mListener;
    SensorManager mSensorManager;
    Sensor mAccel;
    float initialX;
    float initialY;
    float initialZ;
    boolean isFirstEvent;
    int timeSinceLastPositionChanged;
    MineSweeperTimerTask2 myTimerTask;

    public class SensorServiceBinder extends Binder {


        void registerListener(SensorServiceListener listener) {
            Log.d("Binder", "registering...");
            mListener = listener;
        }

        void unregisterListener(SensorServiceListener listener) {
            Log.d("Binder", "unregistering...");
            mListener = null;
        }

        void startSensors(boolean isFirstTImeFromApp) {
            isFirstEvent = isFirstTImeFromApp;
            mSensorManager.registerListener(SensorsService.this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
        }

        void stopSensors() {
            mSensorManager.unregisterListener(SensorsService.this);
        }

    }


    public SensorsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //TODO if SensotManager != null

        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mAccel != null) {
            Log.d("Sensors ouput", "Accelerometer avilable");
        } else {
            Log.d("Sensors ouput", "Accelerometer NOT Availible");
        }

//        timeSinceLastPositionChanged = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // Sensor event listener callbacks


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        initialize(event);

        double absDiffX = Math.abs(initialX - event.values[0]);
        double absDiffY = Math.abs(initialY - event.values[1]);
        double absDiffZ = Math.abs(initialZ - event.values[2]);

        Log.d("event", "" + event.values[0] + " " + event.values[1] + " " + event.values[2]);
        Log.d("Diff", "" + absDiffX + " " + absDiffY + " " + absDiffZ);

        SensorServiceListener.ALARM_STATE previousState = currentAlarmState;
        if (absDiffX > THRESHOLD || absDiffY > THRESHOLD || absDiffZ > THRESHOLD) {
            this.currentAlarmState = SensorServiceListener.ALARM_STATE.NOT_ON_POSITION;
        } else {
            this.currentAlarmState = SensorServiceListener.ALARM_STATE.ON_POSITION;
        }

        if (previousState != currentAlarmState) {
            timeSinceLastPositionChanged = handleTimer(currentAlarmState);
            mListener.alarmStateChanged(currentAlarmState, timeSinceLastPositionChanged);
        }
    }

    private int handleTimer(SensorServiceListener.ALARM_STATE currentAlarmState) {
        if (currentAlarmState == SensorServiceListener.ALARM_STATE.NOT_ON_POSITION) {
            runTimer();
            return 0;
        } else { // ON_POSITION
            return timeSinceLastPositionChanged;
        }
    }

    private void runTimer() {
        Timer timer = new Timer();
        if (myTimerTask != null)
            myTimerTask.cancel();
        myTimerTask = new MineSweeperTimerTask2();
        timer.schedule(myTimerTask, 0, 1000);
    }

    class MineSweeperTimerTask2 extends TimerTask {
        private long firstClickTime = System.currentTimeMillis();

        @Override
        public void run() {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    timeSinceLastPositionChanged = (int) ((System.currentTimeMillis() - firstClickTime) / 1000);
                    if (timeSinceLastPositionChanged % 5 == 0) {
                        if (timeSinceLastPositionChanged >= 10 && currentAlarmState == SensorServiceListener.ALARM_STATE.NOT_ON_POSITION) {
                            mListener.alarmStateChanged(currentAlarmState, timeSinceLastPositionChanged);
                            timeSinceLastPositionChanged %= 10;
                        } else if (timeSinceLastPositionChanged >= 5 && currentAlarmState == SensorServiceListener.ALARM_STATE.NOT_ON_POSITION)
                            mListener.alarmStateChanged(currentAlarmState, timeSinceLastPositionChanged);
                    }
                }
            }).start();
        }
    }

    private void initialize(SensorEvent event) {
        if (isFirstEvent) {
            initialX = event.values[0];
            initialY = event.values[1];
            initialZ = event.values[2];
            isFirstEvent = false;
        }
    }
}
