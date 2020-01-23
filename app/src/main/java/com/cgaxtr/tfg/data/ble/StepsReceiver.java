package com.cgaxtr.tfg.data.ble;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cgaxtr.tfg.data.ble.listeners.StepsListener;
import com.cgaxtr.tfg.data.local.DeviceManager;
import com.cgaxtr.tfg.data.local.SessionManager;
import com.cgaxtr.tfg.data.model.Measurement;
import com.cgaxtr.tfg.data.remote.ApiHelper;

import static java.lang.System.currentTimeMillis;

public class StepsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        BleDevice device = new BleDevice(new DeviceManager().getDevice().getMacAddress());

        device.connect();

        while(!device.readSteps(new StepsListener() {
            @Override
            public void onStepsRead(int value) {
                Log.d("ALARMA", value+"");
                Measurement measurement = new Measurement(new SessionManager().getId(), value, currentTimeMillis()/1000);
                new ApiHelper().uploadStepsMeasure(measurement, new SessionManager().getJWT());
            }
        }));
    }
}
