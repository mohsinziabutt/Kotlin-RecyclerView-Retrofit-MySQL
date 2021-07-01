package com.mohsinziabutt.firstkotlinproject.api

import com.mohsinziabutt.firstkotlinproject.models.DefaultResponse
import com.mohsinziabutt.firstkotlinproject.models.LoginResponse
import com.mohsinziabutt.firstkotlinproject.models.PostResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api
{
    @FormUrlEncoded
    @POST("register.php")
    fun createUser(
        @Field("email") email:String,
        @Field("name") name:String,
        @Field("password") password:String
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ):Call<LoginResponse>

    @GET("posts")
    fun getAllPosts(
    ):Call<List<PostResponse>>
    //  this @get Request is used to get Array of objects e.g. [ {}, {} ]
    //    [
    //        {
    //            "UserId": 1,
    //            "Id": 1,
    //            "Title": "to provide or to reject the blind are welcome option to find"
    //            "Body": "And it takes \ nsuscipit follow accepted lightly."
    //        }
    //        {
    //            "UserId": 1,
    //            "Id": 2,
    //            "Title": "that was"
    //            "Body": "is existed at the time of life."
    //        }
    //    ]
}