package com.example.cloudinteractive.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cloudinteractive.model.HttpMethods;

//圖片下載的view model
public class ImageDownloadViewModel extends AndroidViewModel {

    private final LiveData<byte[]> downloadObservable;
    public ImageDownloadViewModel(@NonNull HttpMethods httpMethods, @NonNull Application application, String downloadUrl) {
        super(application);

        // If any transformation is needed, this can be simply done by Transformations class ...
        downloadObservable = httpMethods.downloadImage(downloadUrl);
    }




    /**
     * Expose the LiveData Image List so the UI can observe it.
     */
    public LiveData<byte[]> getDownloadObservable() {
        return downloadObservable;
    }
}
