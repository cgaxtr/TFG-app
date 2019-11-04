package com.cgaxtr.tfg.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cgaxtr.tfg.Controller;
import com.cgaxtr.tfg.data.model.User;

public class SessionManager {

    private static final String KEY_LOGGEDIN = "logedIn";
    private static final String KEY_JWT = "jwt";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_DATE = "date";

    private static final String NAME = "SessionManger";

    private SharedPreferences sharedPreferences;
    private Editor editor;

    public SessionManager(){
        sharedPreferences = Controller.getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(User user){

        if (user == null)
            throw new IllegalStateException("User not set");

        editor.putBoolean(KEY_LOGGEDIN, true);
        editor.putString(KEY_JWT, user.getJwt());
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_SURNAME, user.getSurname());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putLong(KEY_DATE, user.getDate());

        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(KEY_LOGGEDIN,false);
    }

    public String getJWT(){
        return sharedPreferences.getString(KEY_JWT, null);
    }

    public int getId(){
        return sharedPreferences.getInt(KEY_ID, -1);
    }

    public void logOut(){
        editor.clear();
        editor.commit();
    }

    public User getUserData(){

        int id = sharedPreferences.getInt(KEY_ID, 0);
        String name = sharedPreferences.getString(KEY_NAME,null);
        String surname = sharedPreferences.getString(KEY_SURNAME,null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        long date = sharedPreferences.getLong(KEY_DATE, 0);

        User u = new User();
        u.setId(id);
        u.setName(name);
        u.setSurname(surname);
        u.setEmail(email);
        u.setDate(date);

        return u;
    }
}
