package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class MachanLoginMod {
    private String status;
    private String token;

    @SerializedName("message")
    private String message;

    public String getStatus(){
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
