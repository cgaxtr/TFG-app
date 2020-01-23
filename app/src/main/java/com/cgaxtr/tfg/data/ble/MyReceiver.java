package com.cgaxtr.tfg.data.ble;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cgaxtr.tfg.data.ble.listeners.HeartRateListener;
import com.cgaxtr.tfg.data.ble.listeners.StepsListener;
import com.cgaxtr.tfg.data.local.DeviceManager;
import com.cgaxtr.tfg.data.local.SessionManager;
import com.cgaxtr.tfg.data.model.Measurement;
import com.cgaxtr.tfg.data.remote.ApiHelper;
import com.cgaxtr.tfg.utils.AlarmUtils;

import static java.lang.System.currentTimeMillis;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();
        Log.d("ALARMA", "alarma");
        AlarmUtils.scheduleAlarmSteps();
        BleDevice device = new BleDevice(new DeviceManager().getDevice().getMacAddress());

        device.connect();
        /*
        while(!device.readSteps(new StepsListener() {
            @Override
            public void onStepsRead(int value) {
            Log.d("ALARMA", value+"");
            }
        })){
        }
         */

        while(!device.readHeartRate(new HeartRateListener() {
            @Override
            public void onHeartRateRead(int value) {
                Log.d("ALARMA", value+"");

                Measurement measurement = new Measurement(new SessionManager().getId(), value, currentTimeMillis()/1000);
                new ApiHelper().uploadHeartRateMeasure(measurement, new SessionManager().getJWT());
            }
        }));
    }
}
