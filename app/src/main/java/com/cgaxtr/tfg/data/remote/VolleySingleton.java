package com.cgaxtr.tfg.data.remote;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cgaxtr.tfg.Controller;

public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    private VolleySingleton() {
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(){
        if (mInstance == null){
            mInstance = new VolleySingleton();
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(Controller.getAppContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
