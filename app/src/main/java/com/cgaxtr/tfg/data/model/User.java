package com.cgaxtr.tfg.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private long date;
    private String jwt;

    public User(){}

    public User(JSONObject json){

        try{
            JSONObject user = json.getJSONObject("user");
            id = user.getInt("id");
            role = user.getString("role");
            name = user.getString("name");
            surname = user.getString("surname");
            email = user.getString("email");
            date = user.getLong("birthdate");
            jwt = json.getString("token");
        }catch (JSONException e){
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getJwt(){
        return this.jwt;
    }
}
