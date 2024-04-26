package com.example.dogapplication.domain.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogapplication.DogApplication;
import com.example.dogapplication.data.api.DogApi;
import com.example.dogapplication.data.api.models.DogApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogImageViewModel extends ViewModel {
    private DogApi api = DogApplication.getInstance().getApi();

    private MutableLiveData<String> _dogImageUrl = new MutableLiveData<>(null);
    public LiveData<String> dogImageUrl() {
        return _dogImageUrl;
    }

    public void getNewImage() {
        api.getDogImage().enqueue(new Callback<DogApiResponse>() {
            @Override
            public void onResponse(Call<DogApiResponse> call, Response<DogApiResponse> response) {
                if(response.isSuccessful()) {
                    _dogImageUrl.setValue(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<DogApiResponse> call, Throwable t) {
                _dogImageUrl.setValue(null);
            }
        });
    }
}
