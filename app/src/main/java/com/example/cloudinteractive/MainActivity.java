package com.example.cloudinteractive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cloudinteractive.databinding.FirstViewBinding;
import com.example.cloudinteractive.databinding.SecondViewBinding;
import com.example.cloudinteractive.viewmodel.FirstViewModel;

//第一個頁面
public class MainActivity extends AppCompatActivity {

    FirstViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setFirstViewModel(new FirstViewModel(getApplication(),this));
    }


}