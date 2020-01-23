package com.cgaxtr.tfg.presenter;


import android.util.Log;

import com.cgaxtr.tfg.data.ble.BleManager;
import com.cgaxtr.tfg.data.ble.listeners.StepsListener;
import com.cgaxtr.tfg.data.local.DeviceManager;
import com.cgaxtr.tfg.data.local.SessionManager;
import com.cgaxtr.tfg.data.remote.ApiHelper;
import com.cgaxtr.tfg.data.remote.ResultListener;
import com.cgaxtr.tfg.utils.AlarmUtils;
import com.cgaxtr.tfg.view.views.HomeFragmentView;

import java.util.List;

public class HomePresenter {

    private HomeFragmentView view;
    private BleManager bleManager;
    private DeviceManager deviceManager;
    private int steps;
    private ApiHelper apiHelper;
    private SessionManager sessionManager;

    public HomePresenter(HomeFragmentView view){
        this.view = view;
        bleManager = BleManager.getInstance();
        deviceManager = new DeviceManager();
        apiHelper = new ApiHelper();
        sessionManager = new SessionManager();
        AlarmUtils.scheduleAlarmSteps();
    }

    public void onCreateView(){
        bleManager.connectToDevice(deviceManager.getDevice());
        view.setProgress((steps* 100)/10000);
        view.setData(steps);

        getAvailableTests();

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

    private void getAvailableTests(){
        apiHelper.getAvailableTests(new ResultListener<List<String>>() {
            @Override
            public void onSuccessResult(List<String> list) {
                if(list.size() > 0){
                    view.setNotification("Tienes " + list.size() + " test para contestar");
                }
            }

            @Override
            public void onErrorResult(String s) {

            }
        }, sessionManager.getId(), sessionManager.getJWT());
    }
}

