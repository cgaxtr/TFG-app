package com.cgaxtr.tfg.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.cgaxtr.tfg.Controller;
import com.cgaxtr.tfg.data.ble.HeartRateReceiver;
import com.cgaxtr.tfg.data.ble.StepsReceiver;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmUtils {

    public static void scheduleAlarmHeartRate(){
        AlarmManager alarmManager= (AlarmManager) Controller.getAppContext().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(Controller.getAppContext(), HeartRateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Controller.getAppContext(), 500, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 120000, pendingIntent);
    }

    public static void scheduleAlarmSteps(){
        AlarmManager alarmManager= (AlarmManager) Controller.getAppContext().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(Controller.getAppContext(), StepsReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Controller.getAppContext(), 600, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);


        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
