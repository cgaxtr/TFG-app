package com.cgaxtr.tfg.view.views;


import java.util.List;

public interface ProfileFragmentView {
    void setDataUser(String name, String email, String date);
    void setDataDevice(String name, String mac, String remainBattery);
    void setDataStepsChart(List<Integer> values, List<String> xAxis);
    void setDataHeartRateChart(List<Integer> values, List<String> xAxis);
    void loadLoginActivity();
    void showBatteryData(String value);
}
