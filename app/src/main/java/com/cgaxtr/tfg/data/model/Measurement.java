package com.cgaxtr.tfg.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Measurement {

    private static final String ID_USER = "idUser";
    private static final String VALUE = "value";
    private static final String TIMESTAMP = "timestamp";

    private int idUser;
    private int value;
    private long timestamp;

    public Measurement(JSONObject json){
        try{
            idUser = json.getInt(ID_USER);
            value = json.getInt(VALUE);
            timestamp = json.getLong(TIMESTAMP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
