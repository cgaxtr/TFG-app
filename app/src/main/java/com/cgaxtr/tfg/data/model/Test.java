package com.cgaxtr.tfg.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private String name;
    private List<Question> questions;

    public Test(){
        name = "";
        questions = new ArrayList<>();
    }

    public Test(JSONObject js){
        this();
        try{
            name = js.getString("name");

            JSONArray list = js.getJSONArray("questions");

            for(int i = 0; i <list.length(); i++){
                Question q = new Question(list.getJSONObject(i));
                questions.add(q);

            }
        }catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
