package com.cgaxtr.tfg.data.remote;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cgaxtr.tfg.data.model.Measurement;
import com.cgaxtr.tfg.data.model.Test;
import com.cgaxtr.tfg.data.model.User;
import com.cgaxtr.tfg.utils.UrlsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.cgaxtr.tfg.utils.UrlsApi.*;


public class ApiHelper {

    private static final String DEFAULT = "Ups, algo salío mal";
    private static final String TIME_OUT = "Error, límite de tiempo alcanzado";
    private static final String AUTH_FAILURE = "Error en las credenciales";
    private static final String SEVER_ERROR = "Error en el servidor";
    private static final String NETWORK_ERROR = "Error de red";
    private static final String PARSE_ERROR = "Error en la respuesta del servidor";
    private static final String JSON_ERROR = "Error en los datos enviados";

    private RequestQueue queue = VolleySingleton.getInstance().getRequestQueue();

    public void login(String email, String pass, final ResultListener listener){

        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("pass", pass);
        } catch (JSONException e) {
            listener.onErrorResult(JSON_ERROR);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, UrlsApi.LOGIN, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onSuccessResult(new User(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResult(errorToString(error));
            }
        });

        queue.add(request);
    }

    public void getTest(final ResultListener<Test> listener, String name) {
        String url = GET_TEST_NAME.replace("{name}", "dass21");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                listener.onSuccessResult(new Test(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResult(null);
            }
        });

        queue.add(request);
    }

    public void getAvailableTests(final ResultListener<List<String>> listener, int id){
        String url = GET_AVAILABLE_TEST.replace("{id}", Integer.toString(id));

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<String> list = new ArrayList<>();
                try {
                    JSONArray l= response.getJSONArray("availableTests");
                    for(int i = 0; i < l.length(); i++){
                        list.add(l.getString(i));
                    }
                } catch (JSONException e) {
                    //todo
                    e.printStackTrace();
                }

                listener.onSuccessResult(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //todo
                listener.onErrorResult("");
            }
        });

        queue.add(request);
    }

    //todo terminar
    public void uploadTest(com.cgaxtr.tfg.data.model.Response responses, final ResultListener listener){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, UrlsApi.SEND_RESPONSE, responses.toJSON(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("OK", "onResponse");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", "onErrorResponse");
                if(error.getMessage() != null) {
                    Log.d("ERROR", error.getMessage());
                    Log.d("ERROR", error.getCause().toString());
                }

            }
        });

        queue.add(request);
    }

    public void getHeartRateMeasurements(int id, final ResultListener<List<Measurement>> listener){
        String url = GET_HEARTRATE.replace("{id}" , Integer.toString(id));

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonList = response.getJSONArray("measurements");
                    List<Measurement> list = new ArrayList<>();

                    for(int i = 0; i < jsonList.length(); i++){
                        list.add(new Measurement(jsonList.getJSONObject(i)));
                    }

                    listener.onSuccessResult(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //todo
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });

        queue.add(request);
    }

    public void getStepsMeasurements(int id, final ResultListener<List<Measurement>> listener){
        String url = GET_STEPS.replace("{id}" , Integer.toString(id));

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonList = response.getJSONArray("measurements");
                    List<Measurement> list = new ArrayList<>();

                    for(int i = 0; i < jsonList.length(); i++){
                        list.add(new Measurement(jsonList.getJSONObject(i)));
                    }

                    listener.onSuccessResult(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //todo
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });

        queue.add(request);
    }

    private String errorToString(VolleyError error){

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            return TIME_OUT;
        } else if (error instanceof AuthFailureError) {
            return AUTH_FAILURE;
        } else if (error instanceof ServerError) {
            return SEVER_ERROR;
        } else if (error instanceof NetworkError) {
            return NETWORK_ERROR;
        } else if (error instanceof ParseError) {
            return PARSE_ERROR;
        }

        return DEFAULT;
    }

}
