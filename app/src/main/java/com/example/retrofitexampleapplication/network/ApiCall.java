package com.example.retrofitexampleapplication.network;

import com.example.retrofitexampleapplication.data.UserDataModel;
import com.example.retrofitexampleapplication.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCall {
    public static final String GET_URL = "users";
    public static final String POST_URL = "createDbUser";

    @GET(GET_URL)
    Call<List<UserDataModel>> getUserInfo();

    @GET(POST_URL)
    Call<User> createUser();

}
