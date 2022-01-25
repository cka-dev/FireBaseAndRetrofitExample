package com.example.retrofitexampleapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("data")
    @Expose
    private User data;

    public UserData(User data) {
        this.data = data;
    }
}
