package com.example.cloudinteractive.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpMethods {
    public static final String TAG = "HttpMethods";

    private static final String baseUrl = "https://jsonplaceholder.typicode.com";

    private static final int TIME_OUT=5;
    private static final int WRITE_TIME_OUT=5;
    private static final int READ_TIME_OUT=5;
    private Retrofit retrofit;
    private ApiService apiService;

    //Initialize http requests
    private HttpMethods(){
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(READ_TIME_OUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);

        retrofit= new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService=retrofit.create(ApiService.class);

    }

    public ApiService getApiService(){
        return apiService;
    }

    private static class singalInstance {
        public static final HttpMethods instance = new HttpMethods();
    }

    public  static HttpMethods getInstance(){
        return singalInstance.instance;
    }

    public LiveData<List<ImageContent>> getImageList() {
        final MutableLiveData<List<ImageContent>> data = new MutableLiveData<>();

        Log.d(TAG,"get data");
        apiService.getImageList().enqueue(new Callback<List<ImageContent>>() {
            @Override
            public void onResponse(Call<List<ImageContent>> call, Response<List<ImageContent>> response) {
                Log.d(TAG,"get data success");
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ImageContent>> call, Throwable t) {
                Log.d(TAG,"get data fail "+t.toString());
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<byte[]> downloadImage(String url){
        final MutableLiveData<byte[]> data = new MutableLiveData<>();
        apiService.downloadImage(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                try {
                    byte[] bytes = responseBody.bytes();
                    data.setValue(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
