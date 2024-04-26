package com.example.dogapplication.data.api;

import com.example.dogapplication.data.api.models.DogApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface DogApi {
    @GET("/api/breeds/image/random")
    public Call<DogApiResponse> getDogImage();
}
