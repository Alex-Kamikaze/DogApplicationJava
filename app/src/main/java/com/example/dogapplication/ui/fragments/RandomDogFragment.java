package com.example.dogapplication.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dogapplication.R;
import com.example.dogapplication.databinding.FragmentRandomDogBinding;
import com.example.dogapplication.domain.viewmodels.DogImageViewModel;
import com.example.dogapplication.domain.workers.SaveImageWorker;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RandomDogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomDogFragment extends Fragment {

    FragmentRandomDogBinding binding;
    DogImageViewModel viewModel;

    public RandomDogFragment() {}

    public static RandomDogFragment newInstance(String param1, String param2) {
        return new RandomDogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DogImageViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRandomDogBinding.inflate(inflater);
        binding.newImageButton.setOnClickListener(v -> viewModel.getNewImage());
        viewModel.dogImageUrl().observe(getViewLifecycleOwner(), dogImageUrl -> {
            Picasso.get().load(dogImageUrl).into(binding.dogImage);
        });
        binding.saveButton.setOnClickListener(v -> {
            String imageUrl = viewModel.dogImageUrl().getValue();
            Data workerInput = new Data.Builder()
                    .putString("image_url", imageUrl)
                    .build();
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(SaveImageWorker.class).setInputData(workerInput).build();
            WorkManager.getInstance(requireContext()).enqueue(workRequest);
            Toast.makeText(requireContext(), "Сохраняем изображение во внутреннее хранилище", Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}