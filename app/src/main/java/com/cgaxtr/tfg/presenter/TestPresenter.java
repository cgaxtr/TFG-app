package com.cgaxtr.tfg.presenter;

import android.util.Log;

import com.cgaxtr.tfg.data.local.SessionManager;
import com.cgaxtr.tfg.data.model.Question;
import com.cgaxtr.tfg.data.model.Response;
import com.cgaxtr.tfg.data.model.Test;
import com.cgaxtr.tfg.data.remote.ApiHelper;
import com.cgaxtr.tfg.data.remote.ResultListener;
import com.cgaxtr.tfg.view.views.TestFragmentView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TestPresenter {

    private ApiHelper api;
    private TestFragmentView view;
    private Test test;
    private SessionManager sessionManager;
    private List<String> availableTests;

    public TestPresenter(TestFragmentView view){
        api = new ApiHelper();
        this.view = view;
        sessionManager = new SessionManager();
        availableTests = new ArrayList<>();
    }

    public void loadTest(){
        view.startLoading();

        if(availableTests.size() != 0 && test == null){
            api.getTest(new ResultListener<Test>() {
                @Override
                public void onSuccessResult(Test obj) {
                    test = obj;
                    view.showList(test.getQuestions());
                    view.stopLoading();
                }

                @Override
                public void onErrorResult(String s) {
                    view.stopLoading();
                }
            }, availableTests.get(0), sessionManager.getJWT());
        }else{
            view.showList(new ArrayList<Question>() {});
            view.stopLoading();
            view.showNotification("Ya no tienes más tests para contestar hoy");
        }

        if(test != null) {
            view.showList(test.getQuestions());
            view.stopLoading();
        }


    }

    public void getAvailableTests(){
        api.getAvailableTests(new ResultListener<List<String>>() {
            @Override
            public void onSuccessResult(List<String> list) {
                availableTests = list;
                Log.d("AVAILABLE_TESTS", "finish");
            }

            @Override
            public void onErrorResult(String s) {

            }
        }, sessionManager.getId(), sessionManager.getJWT());
    }

    public void sendResponses(TreeMap<Integer, Integer> responses){
        Response r = new Response(sessionManager.getId(), test.getName(), System.currentTimeMillis() / 1000, responses);

        api.uploadTest(r, new ResultListener<String>() {
            @Override
            public void onSuccessResult(String obj) {
                test = null;
                availableTests.remove(0);
                loadTest();
            }

            @Override
            public void onErrorResult(String s) {

            }
        }, sessionManager.getJWT());
        //test = null;
        //availableTests.remove(0);
    }
}
