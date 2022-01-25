package com.example.retrofitexampleapplication.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient<S> {
    public static String TAG = "RetrofitBuilderClass";
    private final S service;

    public RetrofitClient(String baseUrl, Class<S> serviceClass) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new UserIdToken())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(serviceClass);
    }

    public S getService() {
        return service;
    }
}
