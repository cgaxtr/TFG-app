package com.cgaxtr.tfg.presenter;


import com.cgaxtr.tfg.data.ble.BleManager;
import com.cgaxtr.tfg.data.ble.listeners.StepsListener;
import com.cgaxtr.tfg.data.local.DeviceManager;
import com.cgaxtr.tfg.utils.AlarmUtils;
import com.cgaxtr.tfg.view.views.HomeFragmentView;

public class HomePresenter {

    private HomeFragmentView view;
    private BleManager bleManager;
    private DeviceManager deviceManager;
    private int steps;

    public HomePresenter(HomeFragmentView view){
        this.view = view;
        bleManager = BleManager.getInstance();
        deviceManager = new DeviceManager();
        AlarmUtils.scheduleAlarmSteps();
    }

    public void onCreateView(){
        bleManager.connectToDevice(deviceManager.getDevice());
        view.setProgress((steps* 100)/10000);
        view.setData(steps);

        //TODO
        while(!bleManager.readSteps(new StepsListener() {
            @Override
            public void onStepsRead(int value) {
                steps = value;
                view.setProgress((value* 100)/10000);
                view.setData(value);
            }
        }));
    }
}

