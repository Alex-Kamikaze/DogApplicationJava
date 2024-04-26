package com.example.dogapplication.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogapplication.databinding.FragmentDogGalleryBinding;
import com.example.dogapplication.domain.adapters.DogGalleryViewPagerAdapter;
import com.example.dogapplication.domain.viewmodels.DogListViewModel;

import java.util.ArrayList;

public class DogGalleryFragment extends Fragment {
    FragmentDogGalleryBinding binding;
    DogListViewModel viewModel;
    int currentDogImagePosition;

    public DogGalleryFragment() {
        // Required empty public constructor
    }

    public static DogGalleryFragment newInstance() {
        return new DogGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DogListViewModel.class);
        currentDogImagePosition = DogGalleryFragmentArgs.fromBundle(getArguments()).getCurrentDogImagePosition();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDogGalleryBinding.inflate(inflater, container, false);
        viewModel.updateFileList(requireContext());
        ArrayList<String> dogImageList = viewModel.files().getValue();
        DogGalleryViewPagerAdapter adapter = new DogGalleryViewPagerAdapter(dogImageList, requireContext());
        Log.d("IMAGES", viewModel.files().getValue().toString());
        binding.imageViewPager.setAdapter(adapter);
        binding.imageViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.imageViewPager.setCurrentItem(currentDogImagePosition);
        return binding.getRoot();
    }
}