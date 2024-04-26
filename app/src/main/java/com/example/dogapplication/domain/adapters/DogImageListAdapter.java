package com.example.dogapplication.domain.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapplication.databinding.DogImageListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DogImageListAdapter extends RecyclerView.Adapter<DogImageListAdapter.ViewHolder> {

    private ArrayList<String> dogImageUrls;
    private Context context;

    public interface onImageClickedListener {
        void onImageClicked(int position);
    }

    private onImageClickedListener listener;

    public DogImageListAdapter(ArrayList<String> dogImageUrls, Context context, onImageClickedListener listener) {
        this.dogImageUrls = dogImageUrls;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DogImageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DogImageListItemBinding binding = DogImageListItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DogImageListAdapter.ViewHolder holder, int position) {
        Log.d("LOADING IMAGE", Uri.parse(context.getFilesDir()+ "/" + dogImageUrls.get(position)).toString());
        Picasso.get().load("file:///" + context.getFilesDir() + "/" + dogImageUrls.get(position))
                .resize(400, 400)
                .centerCrop()
                .into(holder.binding.dogImage);
        holder.binding.dogImage.setOnClickListener(v -> {
            this.listener.onImageClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return dogImageUrls.size();
    }

    public void updateData(ArrayList<String> newImages) {
        this.dogImageUrls = newImages;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        DogImageListItemBinding binding;
        public ViewHolder(@NonNull DogImageListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
