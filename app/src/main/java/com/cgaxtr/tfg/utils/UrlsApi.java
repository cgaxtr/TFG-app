package com.cgaxtr.tfg.utils;

public class UrlsApi {
    //private static final String BASE_PATH = "http://192.168.1.100:8080/tfg/public/";

   // private static final String BASE_PATH = "http://192.168.0.158/api/public/";

    private static final String BASE_PATH = "http://137.101.176.12/tfg/public/";

    private static final String BASE_PATH_API = BASE_PATH + "api/";
    public static final String LOGIN = BASE_PATH + "auth/login";
    public static final String GET_AVAILABLE_TEST = BASE_PATH_API + "user/{id}/availabletests";
    public static final String GET_TEST_NAME = BASE_PATH_API + "test/{name}";
    public static final String SEND_RESPONSE = BASE_PATH_API + "test";
    public static final String GET_STEPS = BASE_PATH_API + "measurement/steps/{id}";
    public static final String GET_HEARTRATE = BASE_PATH_API + "measurement/heartrate/{id}";
    public static final String UPLOAD_HEARTRATE = BASE_PATH_API + "measurement/heartrate";
    public static final String UPLOAD_STEPS = BASE_PATH_API + "measurement/steps";
}