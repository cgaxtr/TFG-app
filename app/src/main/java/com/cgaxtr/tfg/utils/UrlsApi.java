package com.cgaxtr.tfg.utils;

public class UrlsApi {
    private static final String BASE_PATH = "http://192.168.0.155/api/public/";
    private static final String BASE_PATH_API = BASE_PATH + "api/";
    public static final String LOGIN = BASE_PATH + "auth/login";
    public static final String GET_AVAILABLE_TEST = BASE_PATH_API + "user/{id}/availabletests";
    public static final String GET_TEST_NAME = BASE_PATH_API + "test/{name}";
    public static final String SEND_RESPONSE = BASE_PATH_API + "test";
    public static final String GET_STEPS = BASE_PATH_API + "measurement/steps/{id}";
    public static final String GET_HEARTRATE = BASE_PATH_API + "measurement/heartrate/{id}";
}