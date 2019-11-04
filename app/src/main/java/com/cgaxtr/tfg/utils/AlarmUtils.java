package com.cgaxtr.tfg.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.cgaxtr.tfg.Controller;
import com.cgaxtr.tfg.data.ble.MyReceiver;

import static android.content.Context.ALARM_SERVICE;

public class AlarmUtils {

    public static void scheduleAlarmSteps(){
        AlarmManager alarmManager= (AlarmManager) Controller.getAppContext().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(Controller.getAppContext(), MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Controller.getAppContext(), 500, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000, pendingIntent);
    }

}
