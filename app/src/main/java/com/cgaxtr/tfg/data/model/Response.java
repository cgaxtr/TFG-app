package com.cgaxtr.tfg.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class Response {

    private static final String KEY_TEST_NAME = "testName";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_RESPONSES = "responses";
    private static final String KEY_QUESTION_ID = "questionId";
    private static final String KEY_RESPONSE_ID = "responseId";

    private int userId;
    private String testName;
    private long timestamp;
    private TreeMap<Integer, Integer> responses;

    public Response(int userId, String testName, long timestamp, TreeMap<Integer, Integer> responses) {
        this.userId = userId;
        this.testName = testName;
        this.timestamp = timestamp;
        this.responses = responses;
    }


    public JSONObject toJSON(){
        JSONObject js = new JSONObject();

        try {
            js.put(KEY_TEST_NAME, testName);
            js.put(KEY_USER_ID, userId);
            js.put(KEY_TIMESTAMP, timestamp);

            JSONArray responses = new JSONArray();

            for(Map.Entry<Integer,Integer> entry : this.responses.entrySet()) {
                JSONObject aux = new JSONObject();
                aux.put(KEY_QUESTION_ID, entry.getKey());
                aux.put(KEY_RESPONSE_ID, entry.getValue());

                responses.put(aux);
            }

            js.put(KEY_RESPONSES, responses);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js;
    }
}
