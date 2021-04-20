package com.example.cloudinteractive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.example.cloudinteractive.databinding.ThirdViewBinding;
import com.example.cloudinteractive.model.HttpMethods;
import com.example.cloudinteractive.model.ImageContent;
import com.example.cloudinteractive.viewmodel.ImageDownloadViewModel;
import com.example.cloudinteractive.viewmodel.ThirdViewModel;

//第三個頁面
public class ThirdActivity extends AppCompatActivity {

    ThirdViewBinding binding;
    ImageContent imageContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_third);

        binding.setThirdViewModel(new ThirdViewModel(getApplication(),this));

        imageContent = (ImageContent) getIntent().getSerializableExtra("image");
        binding.setImage(imageContent);

        //以防萬一在點擊時圖片還沒加載好
        if(imageContent.getImageBytes()==null){
            final ImageDownloadViewModel viewModel = new ImageDownloadViewModel(HttpMethods.getInstance(),getApplication(),imageContent.getThumbnailUrl());
            observeViewModel(viewModel);
        }

    }

    //圖片下載數據監聽
    private void observeViewModel(ImageDownloadViewModel viewModel) {
        // Update the image when the data changes
        viewModel.getDownloadObservable().observe(this, new Observer<byte[]>() {
            @Override
            public void onChanged(@Nullable byte[] bytes) {
                if (bytes != null) {
                    imageContent.setImageBytes(bytes);
                    binding.setImage(imageContent);
                }
            }
        });
    }
}