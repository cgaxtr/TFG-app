package com.cgaxtr.tfg.data.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.cgaxtr.tfg.Controller;
import com.cgaxtr.tfg.data.ble.listeners.BatteryListener;
import com.cgaxtr.tfg.data.ble.listeners.HeartRateListener;
import com.cgaxtr.tfg.data.ble.listeners.StepsListener;


public class BleDevice {

    private String mac;
    private BluetoothGatt bluetoothGatt;
    private boolean connected = false;

    private BatteryListener batteryListener;
    private HeartRateListener heartRateListener;
    private StepsListener stepsListener;

    private BluetoothGattCallback callback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                bluetoothGatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                connected = false;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.d("SERVICES", "services discovered");
            connected = true;
            configureHeartRate();
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);

            //Log.d("STEPS", String.valueOf(characteristic.getValue()));
            final byte[] data = characteristic.getValue();

            if (characteristic.getUuid().equals(BluetoothServices.Basic.steps)){
                int steps = ((((data[1] & 255) | ((data[2] & 255) << 8))) );
                stepsListener.onStepsRead(steps);
            }else if (characteristic.getUuid().equals(BluetoothServices.Basic.batteryCharacteristic)){
                int battery = data[1];
                batteryListener.onBatteryRead(battery);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);

            final byte[] data = characteristic.getValue();

            Log.d("HEARTRATE", String.valueOf(data[1]));
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }
    };

    public BleDevice(String mac){
        this.mac = mac;
    }

    public void connect(){
        bluetoothGatt = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mac).connectGatt(Controller.getAppContext(), true, callback);
    }

    public void disconnect(){
        bluetoothGatt.close();
    }

    public boolean readSteps(StepsListener listener){
        if(!connected)
            return  false;

        Log.d("STEPS", String.valueOf(connected));
        stepsListener = listener;
        BluetoothGattService service = bluetoothGatt.getService(BluetoothServices.Basic.service);
        BluetoothGattCharacteristic bchar =service.getCharacteristic(BluetoothServices.Basic.steps);
        bluetoothGatt.readCharacteristic(bchar);

        return true;
    }

    public boolean readHeartRate(HeartRateListener listener){
        if(!connected)
            return  false;

        heartRateListener = listener;
        BluetoothGattCharacteristic characteristic = bluetoothGatt.getService(BluetoothServices.HeartRate.service).getCharacteristic(BluetoothServices.HeartRate.controlCharacteristic);
        characteristic.setValue(new byte[]{21, 2, 1});
        bluetoothGatt.writeCharacteristic(characteristic);

        return true;
    }

    public boolean readBattery(BatteryListener listener){
        if(!connected)
            return  false;

        batteryListener = listener;
        BluetoothGattCharacteristic characteristic = bluetoothGatt.getService(BluetoothServices.Basic.service).getCharacteristic(BluetoothServices.Basic.batteryCharacteristic);
        bluetoothGatt.readCharacteristic(characteristic);

        return true;
    }

    public boolean isConnected(){
        return  connected;
    }

    private void configureHeartRate(){
        BluetoothGattCharacteristic bchar = bluetoothGatt.getService(BluetoothServices.HeartRate.service).getCharacteristic(BluetoothServices.HeartRate.measurementCharacteristic);
        bluetoothGatt.setCharacteristicNotification(bchar, true);
        BluetoothGattDescriptor descriptor = bchar.getDescriptor(BluetoothServices.HeartRate.descriptor);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);
    }

    public String getMac(){
        return mac;
    }
}
