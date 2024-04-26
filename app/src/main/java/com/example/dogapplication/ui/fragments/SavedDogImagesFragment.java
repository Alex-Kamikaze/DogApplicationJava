package com.example.dogapplication.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.dogapplication.databinding.FragmentSavedDogImagesBinding;
import com.example.dogapplication.domain.adapters.DogImageListAdapter;
import com.example.dogapplication.domain.viewmodels.DogListViewModel;

import java.util.ArrayList;

public class SavedDogImagesFragment extends Fragment {

    FragmentSavedDogImagesBinding binding;
    DogListViewModel viewModel;

    public SavedDogImagesFragment() {}
    public static SavedDogImagesFragment newInstance() {
        return new SavedDogImagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DogListViewModel.class);
        viewModel.updateFileList(requireContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSavedDogImagesBinding.inflate(inflater);
        ArrayList<String> imagesUrls = viewModel.files().getValue();
        if(imagesUrls == null) {
            imagesUrls = new ArrayList<>();
        }
        DogImageListAdapter.onImageClickedListener listener = new DogImageListAdapter.onImageClickedListener() {
            @Override
            public void onImageClicked(int position) {
               Navigation.findNavController(binding.getRoot()).navigate(SavedDogImagesFragmentDirections.actionSavedDogImagesFragmentToDogGalleryFragment(position));
            }
        };
        DogImageListAdapter adapter = new DogImageListAdapter(imagesUrls, requireContext(), listener);
        viewModel.files().observe(getViewLifecycleOwner(), images -> {
            adapter.updateData(images);
            Log.d("IMAGES", viewModel.files().getValue().toString());
        });
        binding.dogImageList.setAdapter(adapter);
        binding.pullToRefresh.setOnRefreshListener(() -> {
            viewModel.updateFileList(requireContext());
            binding.pullToRefresh.setRefreshing(false);
        });
        viewModel.isRefreshing().observe(getViewLifecycleOwner(), isRefreshing -> {
            binding.pullToRefresh.setRefreshing(isRefreshing);
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}