package com.example.dogapplication;

import android.app.Application;

import com.example.dogapplication.data.api.DogApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogApplication extends Application {
    private static DogApplication Instance;
    private DogApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
        api = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://dog.ceo")
                .build()
                .create(DogApi.class);
    }

    public static DogApplication getInstance() {
        return Instance;
    }

    public DogApi getApi() {
        return api;
    }
}
