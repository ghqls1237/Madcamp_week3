package com.treasurehunt.madcamp_week3;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//969e55fb388c.ngrok.io
public class RetrofitNotiClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiService apiService = retrofit.create(ApiService.class);
}