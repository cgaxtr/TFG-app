package com.cgaxtr.tfg.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cgaxtr.tfg.Controller;
import com.cgaxtr.tfg.data.model.Device;

public class DeviceManager {

    private static final String NAME = "deviceManager";

    private static final String KEY_MAC = "mac";
    private static final String KEY_NAME = "name";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public DeviceManager(){
        sharedPreferences = Controller.getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void addDevice(Device d){
        editor.putString(KEY_MAC, d.getMacAddress());
        editor.putString(KEY_NAME, d.getName());

        editor.commit();
    }

    public Device getDevice(){

        String mac = sharedPreferences.getString(KEY_MAC, null);
        String name = sharedPreferences.getString(KEY_NAME, null);

        if(mac != null && name != null){
            return new Device(name, mac);
        }

        return null;
    }

    public void removeDevice(){
        editor.clear();
        editor.commit();
    }

    public boolean hasDeviceAssociated(){
        Log.d("DEVICE", sharedPreferences.getString(KEY_NAME, "sin dispositivo"));
        return sharedPreferences.getString(KEY_MAC, null) != null;
    }
}
