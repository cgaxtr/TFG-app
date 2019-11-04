package com.cgaxtr.tfg.data.remote;

public interface ResultListener<T> {

    void onSuccessResult(T obj);
    void onErrorResult(String s);
}
