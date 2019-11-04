package com.cgaxtr.tfg.view.views;

import com.cgaxtr.tfg.data.model.Device;

import java.util.List;

public interface ScanActivityView {
    void showResultScan(List<Device> list);
    void openMainActivity();
    void showError(String error);
    void statLoading();
    void stopLoading();
    void requestPermissions();
    void requestLocation();
}
