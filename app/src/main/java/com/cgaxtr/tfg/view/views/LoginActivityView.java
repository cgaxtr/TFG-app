package com.cgaxtr.tfg.view.views;

public interface LoginActivityView {
    void showMainActivity();
    void showScanDeviceActivity();
    void showError(String errorMsg);
    void showLoading();
    void stopShowingLoading();
}
