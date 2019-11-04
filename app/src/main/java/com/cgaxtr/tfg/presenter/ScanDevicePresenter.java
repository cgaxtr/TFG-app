package com.cgaxtr.tfg.presenter;

import com.cgaxtr.tfg.data.ble.BleManager;
import com.cgaxtr.tfg.data.ble.listeners.ScanListener;
import com.cgaxtr.tfg.data.local.DeviceManager;
import com.cgaxtr.tfg.data.model.Device;
import com.cgaxtr.tfg.view.views.ScanActivityView;

import java.util.List;

public class ScanDevicePresenter implements BasePresenter{

    private ScanActivityView view;
    private BleManager bleManager;
    private DeviceManager manager;
    private boolean permissionGranted, locationGranted;

    public ScanDevicePresenter(ScanActivityView view){

        this.view = view;
        bleManager = BleManager.getInstance();
        manager = new DeviceManager();
        permissionGranted = false;
        locationGranted = false;
    }

    public void scan(){
        view.statLoading();
        bleManager.scanDevices(10000, new ScanListener() {
            @Override
            public void onStop(List<Device> devices) {
                view.showResultScan(devices);
                view.stopLoading();
            }
        });
    }

    public void associateDevice(Device device){
        if (device.getBonded()){
            manager.addDevice(device);
            view.openMainActivity();
        }else{
            view.showError("Es necesario que el dispositivo este vinculado al movil antes de poder vincularlo a la aplicación");
        }
    }

    public void permissionsGranted(boolean active){
        this.permissionGranted = active;

        if (active)
            view.requestLocation();
        else
            view.showError("Para poder buscar dispositivos bluetooth necesitamos el permiso de ubicación");

    }

    public void locationGranted(boolean active){
        this.locationGranted = active;

        if (!active)
            view.showError("Para poder buscar dispositivos bluetooth necesitamos activar la geolocalización");
        else if(locationGranted && permissionGranted)
            scan();
    }

    @Override
    public void onCreate() {
        view.requestPermissions();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        //view.requestLocation();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
