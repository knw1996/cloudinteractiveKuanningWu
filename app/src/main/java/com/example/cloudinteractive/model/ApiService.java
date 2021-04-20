package com.example.cloudinteractive.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET("photos")
    Call<List<ImageContent>> getImageList();

    @GET
    Call<ResponseBody> downloadImage(@Url String url);
}
