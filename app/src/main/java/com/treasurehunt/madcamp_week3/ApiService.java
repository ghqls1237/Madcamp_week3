package com.treasurehunt.madcamp_week3;

import android.location.Location;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/users/login/")
    Call<String> login(@Body User user);

    @POST("/treasure/hunt/")
    Call<String> hunt(@Body DeviceLocation deviceLocation);

    @GET("/users/score/")
    Call<JsonArray> score(@Query("uid") String uid);

//    Call<NotiKey> getNotiKey(@Body NotiRequest notiRequest);
    //
//    @GET("/users/login")
//    Call<String> login_get();
//    @GET("/contacts/")
//    Call<JsonArray> getretrofitdata(@Query("uid") String uid);
//
//    @POST("/contacts/")
//    Call<String> createPost(@Body Contact contact);
//
//    @POST("/images/")
//    Call<String> images(@Body Image image);
//
//    @GET("/images/")
//    Call<JsonArray> getImages(@Query("uid") String uid);
//
//    @DELETE("/images/")
//    Call<String> deleteImages(@Query("pk") String pk);
//
//    @DELETE("/contacts/")
//    Call<String> deletePost(@Query("pk") String pk);
//

//
//    @GET("/seas/")
//    Call<JsonArray> getSeas();
//
//    @GET("/beaches/")
//    Call<JsonArray> getBeaches(@Query("pkk") String pkk);
//
//    @POST("/posts/")
//    Call<String> createPost(@Body Post post);
//
//    @GET("/posts/")
//    Call<JsonArray> getPosts(@Query("beach") String beach);
//
//    @GET("/posts/detail/")
//    Call<PostItem> getPostDetail(@Query("pk") String pk);
//
//    @GET("/comments/")
//    Call<ArrayList<CommentItem>> getComments(@Query("pk") String pk);
//
//    @POST("/comments/")
//    Call<CommentItem> postComment(@Body PostCommentItem comment);
//
//    @GET("/notifications/")
//    Call<JsonArray> getNotis(@Query("uid") String uid);

}