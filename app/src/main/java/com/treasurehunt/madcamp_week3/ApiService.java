package com.treasurehunt.madcamp_week3;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/users/login/")
    Call<String> login(@Body User user);
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