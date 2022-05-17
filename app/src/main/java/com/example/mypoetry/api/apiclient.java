package com.example.mypoetry.api;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiclient {
    public static Retrofit retrofit=null;

    public static Retrofit getRetrofit(){

        if(retrofit==null) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.73/poetry_apis/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())).build();

        }
        return retrofit;
    }
}
