package com.cgaxtr.tfg.data.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;

public class ScannerBle {

    private BluetoothAdapter mBluetoothAdapter;
    private List<BluetoothDevice> devicesList;

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            if(!devicesList.contains(bluetoothDevice))
                devicesList.add(bluetoothDevice);
        }
    };

    public ScannerBle(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        devicesList = new ArrayList<>();
    }

    public void startScan(){
        mBluetoothAdapter.startLeScan(leScanCallback);
    }

    public List<BluetoothDevice> stopScan(){
        mBluetoothAdapter.stopLeScan(leScanCallback);

        return devicesList;
    }
}
