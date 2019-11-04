package com.cgaxtr.tfg.data.ble;


import android.bluetooth.BluetoothDevice;
import android.os.Handler;

import com.cgaxtr.tfg.data.ble.listeners.BatteryListener;
import com.cgaxtr.tfg.data.ble.listeners.HeartRateListener;
import com.cgaxtr.tfg.data.ble.listeners.ScanListener;
import com.cgaxtr.tfg.data.ble.listeners.StepsListener;
import com.cgaxtr.tfg.data.model.Device;

import java.util.ArrayList;
import java.util.List;

import static android.bluetooth.BluetoothDevice.BOND_BONDED;

public class BleManager {

    private static final int REQUEST_CODE = 101;

    private static final BleManager instance = new BleManager();

    private Handler handler;
    private ScannerBle scannerBle;
    private List<BleDevice> connectedDevices;

    public static BleManager getInstance() {
        return instance;
    }

    private BleManager() {
        this.handler = new Handler();
        this.scannerBle = new ScannerBle();
        this.connectedDevices = new ArrayList<>();
    }

    public void scanDevices(int periodMs, final ScanListener listener){
        scannerBle.startScan();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<BluetoothDevice> devicesFound = scannerBle.stopScan();

                List<Device> devices = new ArrayList<>();
                for (BluetoothDevice d: devicesFound) {
                    devices.add(new Device(d.getName(), d.getAddress(), d.getBondState() == BOND_BONDED));
                }

                listener.onStop(devices);
            }
        }, periodMs);
    }

    public void connectToDevice(Device d){
        BleDevice device = new BleDevice(d.getMacAddress());
        device.connect();
        connectedDevices.add(device);
    }

    public boolean readSteps(StepsListener listener){
        BleDevice device = connectedDevices.get(0);
        return device.readSteps(listener);
    }

    public boolean readHeartRate(HeartRateListener listener){
        BleDevice device = connectedDevices.get(0);
        return device.readHeartRate(listener);
    }

    public void readBattery(BatteryListener listener){
        BleDevice device = connectedDevices.get(0);
        device.readBattery(listener);
    }
}
