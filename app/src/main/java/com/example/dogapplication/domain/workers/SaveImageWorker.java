package com.example.dogapplication.domain.workers;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class SaveImageWorker extends Worker {
    private final Context context;
    public SaveImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String newFileName = UUID.randomUUID().toString() + ".jpg";
        String imageUrl = getInputData().getString("image_url");
        if(imageUrl == null) {
            return Result.failure();
        }
        File newImage = new File(context.getFilesDir(), newFileName);
        try {
            Bitmap loadedImage = Picasso.get().load(imageUrl).get();
            newImage.createNewFile();
            ByteArrayOutputStream newImageStream = new ByteArrayOutputStream();
            loadedImage.compress(Bitmap.CompressFormat.JPEG, 95, newImageStream);
            byte[] imageByteData = newImageStream.toByteArray();
            FileOutputStream newImageFileStream = new FileOutputStream(newImage);
            newImageFileStream.write(imageByteData);
            newImageFileStream.flush();
            newImageFileStream.close();
        } catch (IOException e) {
            return Result.failure();
        }
        return Result.success();
    }
}
