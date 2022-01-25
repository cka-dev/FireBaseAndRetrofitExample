package com.example.retrofitexampleapplication.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Subscription {
    @SerializedName("subscriptions")
    private List<Purchases> subscriptions = null;

    public List<Purchases> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Purchases> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
