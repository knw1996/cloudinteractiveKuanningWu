package com.example.cloudinteractive.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cloudinteractive.SecondActivity;

//頁面三的view model
public class ThirdViewModel extends AndroidViewModel {
    private Activity mActivity;
    public ThirdViewModel(@NonNull Application application, Activity activity) {
        super(application);
        this.mActivity = activity;
    }

    public void onBack(View view) {
        mActivity.finish();
    }
}
