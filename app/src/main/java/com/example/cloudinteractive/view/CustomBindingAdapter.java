package com.example.cloudinteractive.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.example.cloudinteractive.R;
import com.example.cloudinteractive.model.ImageContent;


public class CustomBindingAdapter {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("displayImage")
    public static void displayImage(ImageView iv, ImageContent imageContent) {
        if(imageContent.getImageBytes()!=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageContent.getImageBytes(), 0, imageContent.getImageBytes().length);
            iv.setImageBitmap(bitmap);
        }else{
            iv.setImageResource(R.drawable.placeholder);
        }
    }

    @BindingAdapter("displayId")
    public static void displayId(TextView textView, int id) {
        textView.setText(""+id);
    }
}