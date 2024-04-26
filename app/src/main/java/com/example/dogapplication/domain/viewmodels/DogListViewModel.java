package com.example.dogapplication.domain.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DogListViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<String>> _files = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> _isRefreshing = new MutableLiveData<>(false);
    public LiveData<ArrayList<String>> files() {
        return _files;
    }
    public LiveData<Boolean> isRefreshing() {
        return _isRefreshing;
    }

    public void updateFileList(Context context) {
        _isRefreshing.setValue(true);
        String[] filesFromDir = context.fileList();
        ArrayList<String> files = new ArrayList<>();
        for(String file: filesFromDir) {
            if(file.contains(".jpg")) {
                files.add(file);
            }
        }
        _files.setValue(files);
        _isRefreshing.setValue(false);
    }
}
