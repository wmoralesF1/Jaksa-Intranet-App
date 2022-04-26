package com.example.reservas_pasajes.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGeneric<T> {
    @SerializedName("isSuccess")
    @Expose
    private boolean isSuccess;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private T data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
