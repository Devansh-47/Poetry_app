package com.example.mypoetry.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface api_interface {

    @GET("read_api.php")
    Call<Poetry_response> getpoetry();

    @FormUrlEncoded
    @POST("delete_api.php")
    Call<delete_response> deleteportry(@Field("id") String poetry_id);



    @FormUrlEncoded
    @POST("add_poetry_api.php")
    Call<delete_response> addportry(@Field("poetry") String poetry,@Field("poet_name") String poet_name);



    @FormUrlEncoded
    @POST("update_poetry_api.php")
    Call<delete_response> updateportry(@Field("poetry") String poetry,@Field("poet_name") String poet_name,@Field("id") int id);

}
