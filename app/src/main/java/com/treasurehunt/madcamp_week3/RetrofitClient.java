package com.treasurehunt.madcamp_week3;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//969e55fb388c.ngrok.io
public class RetrofitClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://028d864a73b5.ngrok.io") //no 8000
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiService apiService = retrofit.create(ApiService.class);
}

//public class RetrofitClient {
//    private static final String BASE_URL = "http://192.249.18.151/";
//
//    public static RetrofitAPI getApiService(){return getInstance().create(RetrofitAPI.class);}
//
//    private static Retrofit getInstance(){
//        Gson gson = new GsonBuilder().setLenient().create();
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//    }
//}