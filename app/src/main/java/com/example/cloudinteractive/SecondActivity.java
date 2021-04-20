package com.example.cloudinteractive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.cloudinteractive.databinding.SecondViewBinding;
import com.example.cloudinteractive.model.HttpMethods;
import com.example.cloudinteractive.model.ImageContent;
import com.example.cloudinteractive.view.ImageAdapter;
import com.example.cloudinteractive.view.ImageClickCallback;
import com.example.cloudinteractive.viewmodel.SecondViewModel;

import java.util.List;

//第二個頁面
public class SecondActivity extends AppCompatActivity {

    public static final String TAG = "SecondActivity";
    SecondViewBinding binding;
    private ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);

        imageAdapter = new ImageAdapter(this,getApplication(),imageClickCallback);
        binding.projectList.setLayoutManager(new GridLayoutManager(this,4));
        binding.projectList.setAdapter(imageAdapter);
        binding.setIsLoading(true);

        //獲取圖片數據
        final SecondViewModel viewModel = new SecondViewModel(HttpMethods.getInstance(),getApplication());
        observeViewModel(viewModel);
    }

    //圖片數據獲取監聽
    private void observeViewModel(SecondViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getImageListObservable().observe(this, new Observer<List<ImageContent>>() {
            @Override
            public void onChanged(@Nullable List<ImageContent> projects) {
                if (projects != null) {
                    binding.setIsLoading(false);
                    imageAdapter.setProjectList(projects);
                }else{
                    binding.loading.setText("Load Images Failed");
                }
            }
        });
    }

    //圖片點擊回調
    private final ImageClickCallback imageClickCallback = new ImageClickCallback() {
        @Override
        public void onClick(ImageContent image) {
            Log.d(TAG,"onclick image");
            Intent intent = new Intent();
            intent.putExtra("image",image);
            intent.setClass(SecondActivity.this, ThirdActivity.class);
            startActivity(intent);
        }
    };
}