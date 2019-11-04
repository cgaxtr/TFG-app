package com.cgaxtr.tfg.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class BluetoothUtils {

    public static final int REQUEST_ENABLE_BT = 102;

    public static boolean isBluetoothAvailable(){

        return BluetoothAdapter.getDefaultAdapter() != null;
    }

    public static boolean isBluetoothEnabled(){

    return isBluetoothAvailable() && BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public void requestEnableBluetooth(Activity activity){
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }
}
