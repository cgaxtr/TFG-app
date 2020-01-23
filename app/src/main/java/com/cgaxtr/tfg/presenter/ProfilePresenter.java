package com.cgaxtr.tfg.presenter;

import com.cgaxtr.tfg.data.ble.listeners.BatteryListener;
import com.cgaxtr.tfg.data.ble.BleManager;
import com.cgaxtr.tfg.data.local.DeviceManager;
import com.cgaxtr.tfg.data.local.SessionManager;
import com.cgaxtr.tfg.data.model.Device;
import com.cgaxtr.tfg.data.model.Measurement;
import com.cgaxtr.tfg.data.model.User;
import com.cgaxtr.tfg.data.remote.ApiHelper;
import com.cgaxtr.tfg.data.remote.ResultListener;
import com.cgaxtr.tfg.view.views.ProfileFragmentView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfilePresenter {

    private SessionManager sessionManager;
    private DeviceManager deviceManager;
    private ProfileFragmentView view;
    private ApiHelper api;
    private BleManager bleManager;

    public ProfilePresenter(ProfileFragmentView view){
        this.view = view;
        sessionManager = new SessionManager();
        deviceManager = new DeviceManager();
        api = new ApiHelper();
        bleManager = BleManager.getInstance();
    }

    public void logout(){
        sessionManager.logOut();
        view.loadLoginActivity();
    }

    public void disconnectDevice(){
        deviceManager.removeDevice();
        view.exit();
    }

    public void loadUserData(){
        User u = sessionManager.getUserData();

        Date d = new Date(u.getDate()*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(d);
        //String date = DateFormat.getDateInstance().format(d);

        view.setDataUser(u.getName()+ " " + u.getSurname(), u.getEmail(), strDate);
    }

    public void loadDeviceData(){
        Device d = deviceManager.getDevice();
        bleManager.connectToDevice(d);
        bleManager.readBattery(new BatteryListener() {
            @Override
            public void onBatteryRead(int value) {
                view.showBatteryData(value + " %");
            }
        });
        view.setDataDevice(d.getName(), d.getMacAddress(), "Obteniendo información...");
    }

    public void loadHeartRateChart(){
        api.getHeartRateMeasurements(sessionManager.getId(), new ResultListener<List<Measurement>>() {
            @Override
            public void onSuccessResult(List<Measurement> list) {
                List<String> xAxis = new ArrayList<>();
                List<Integer> values = new ArrayList<>();

                for (Measurement measurement: list) {
                    Date d = new Date(measurement.getTimestamp()*1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    xAxis.add(sdf.format(d));
                    values.add(measurement.getValue());
                }

                view.setDataHeartRateChart(values, xAxis);
            }

            @Override
            public void onErrorResult(String s) {

            }
        }, sessionManager.getJWT());
    }

    public void loadStepsChart(){
        api.getStepsMeasurements(sessionManager.getId(), new ResultListener<List<Measurement>>() {
            @Override
            public void onSuccessResult(List<Measurement> list) {
                List<String> xAxis = new ArrayList<>();
                List<Integer> values = new ArrayList<>();

                for (Measurement measurement: list) {
                    Date d = new Date(measurement.getTimestamp()*1000);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
                    xAxis.add(sdf.format(d));
                    values.add(measurement.getValue());
                }

                view.setDataStepsChart(values, xAxis);
            }

            @Override
            public void onErrorResult(String s) {

            }
        }, sessionManager.getJWT());
    }
}
