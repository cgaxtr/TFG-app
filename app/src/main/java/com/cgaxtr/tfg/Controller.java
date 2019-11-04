package com.cgaxtr.tfg;

import android.app.Application;
import android.content.Context;

public class Controller extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Controller.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Controller.context;
    }

}
