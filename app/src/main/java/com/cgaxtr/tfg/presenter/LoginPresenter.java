package com.cgaxtr.tfg.presenter;

import android.text.TextUtils;


import com.cgaxtr.tfg.data.local.DeviceManager;
import com.cgaxtr.tfg.data.local.SessionManager;
import com.cgaxtr.tfg.data.model.User;
import com.cgaxtr.tfg.data.remote.ApiHelper;
import com.cgaxtr.tfg.data.remote.ResultListener;
import com.cgaxtr.tfg.view.views.LoginActivityView;

public class LoginPresenter implements BasePresenter, ResultListener<User>{

    private static final String ERROR_EMAIL = "Campo email vacio";
    private static final String ERROR_PASS = "Campo contraseña vacio";

    private LoginActivityView mView;
    private ApiHelper api;
    private SessionManager sessionManager;
    private DeviceManager deviceManager;


    public LoginPresenter(LoginActivityView mView) {
        this.mView = mView;
        api = new ApiHelper();
        sessionManager = new SessionManager();
        deviceManager = new DeviceManager();
    }

    public void login(String email, String password){

        mView.showLoading();

        if(TextUtils.isEmpty(email)){
            mView.showError(ERROR_EMAIL);
        } else if (TextUtils.isEmpty(password)){
            mView.showError(ERROR_PASS);
        }else {
            api.login(email, password, this);
        }
    }

    public void checkLogin(){
        if(sessionManager.isLoggedIn()) {
            if (!deviceManager.hasDeviceAssociated())
                mView.showScanDeviceActivity();
            else
                mView.showMainActivity();
        }
    }

    public void checkPermissions(){

    }

    @Override
    public void onSuccessResult(User user) {
        mView.stopShowingLoading();
        sessionManager.setLogin(user);
        if(!deviceManager.hasDeviceAssociated())
            mView.showScanDeviceActivity();
        else
            mView.showMainActivity();
    }

    @Override
    public void onErrorResult(String s) {
        mView.showError(s);
        mView.stopShowingLoading();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

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
