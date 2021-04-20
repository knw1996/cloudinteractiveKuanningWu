package com.example.cloudinteractive.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cloudinteractive.model.HttpMethods;
import com.example.cloudinteractive.model.ImageContent;

import java.util.List;

//頁面二的view model
public class SecondViewModel extends AndroidViewModel {

    private final LiveData<List<ImageContent>> imageListObservable;

    public SecondViewModel(@NonNull HttpMethods httpMethods, @NonNull Application application) {
        super(application);

        // If any transformation is needed, this can be simply done by Transformations class ...
        imageListObservable = httpMethods.getImageList();
    }

    /**
     * Expose the LiveData Image List so the UI can observe it.
     */
    public LiveData<List<ImageContent>> getImageListObservable() {
        return imageListObservable;
    }
}
