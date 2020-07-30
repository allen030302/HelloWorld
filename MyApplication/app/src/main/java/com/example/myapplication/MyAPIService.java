package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPIService {
    @GET("albums/1")
    Call<Albums> getAlbums();

    @GET("albums/{id}")
    Call<Albums> getAlbumsById(@Path("id") int id);

    @POST("albums")
    Call<Albums> postAlbums(@Body Albums albums);

    @FormUrlEncoded
    @POST("auth/login")
    Call<MachanLoginMod> postLogin(@Field("account")String account,@Field("password")String password);

}
