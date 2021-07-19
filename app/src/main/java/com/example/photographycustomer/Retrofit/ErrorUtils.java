package com.example.photographycustomer.Retrofit;

public class ErrorUtils {

    private int code;
    private String message;

    public ErrorUtils() {
    }

    public int status() {
        return code;
    }

    public String message() {
        return message;
    }
}
