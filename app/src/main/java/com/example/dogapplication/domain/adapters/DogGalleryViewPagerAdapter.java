package com.example.dogapplication.domain.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapplication.databinding.DogGalleryItemBinding;
import com.example.dogapplication.databinding.DogImageListItemBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class DogGalleryViewPagerAdapter extends RecyclerView.Adapter<DogGalleryViewPagerAdapter.ViewHolder> {

    ArrayList<String> dogImagesUris;
    Context context;

    public DogGalleryViewPagerAdapter(ArrayList<String> dogImagesUris, Context context) {
        this.dogImagesUris = dogImagesUris;
        this.context = context;
    }

    @NonNull
    @Override
    public DogGalleryViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DogGalleryItemBinding binding = DogGalleryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DogGalleryViewPagerAdapter.ViewHolder holder, int position) {
        Log.d("LOADING", "file:///" + context.getFilesDir() + "/"+ dogImagesUris.get(position));
        Picasso.get().load("file:///" + context.getFilesDir() + "/"+ dogImagesUris.get(position)).into(holder.binding.dogImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Log.d("IMAGE_ERROR", Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dogImagesUris.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        DogGalleryItemBinding binding;
        public ViewHolder(@NonNull DogGalleryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
