package com.example.cloudinteractive.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.cloudinteractive.SecondActivity;

//頁面一的view model
public class FirstViewModel extends AndroidViewModel {
    private Context mContext;
    public FirstViewModel(@NonNull Application application, Context context) {
        super(application);
        this.mContext = context;
    }

    public void onRequestApi(View view) {

        Intent intent = new Intent();
        intent.setClass(mContext, SecondActivity.class);
        mContext.startActivity(intent);
    }
}
