package com.cgaxtr.tfg.data.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

public class Question {

    private int id;
    private String question;
    private TreeMap<Integer, String> options;

    public Question(JSONObject js){
        options = new TreeMap<>();

        try{
            id = js.getInt("id");
            question = js.getString("question");
            JSONArray list = js.getJSONArray("options");

            for (int i = 0; i < list.length(); i++){

                int id = list.getJSONObject(i).getInt("id");
                String value = list.getJSONObject(i).getString("option");

                options.put(id,value);
            }

        }catch (JSONException e){
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TreeMap<Integer,String> getOptions() {
        return options;
    }

    public void setOptions(TreeMap<Integer, String> options) {
        this.options = options;
    }
}
