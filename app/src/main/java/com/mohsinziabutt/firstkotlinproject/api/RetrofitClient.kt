package com.mohsinziabutt.firstkotlinproject.api
import android.util.Base64

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// we can create multiple client objects for multiple Base URLs as follow

object LoginRegistrationApiClient {

    private val AUTH = "Basic "+ Base64.encodeToString("mohsinziabutt:123456".toByteArray(), Base64.NO_WRAP)

    private const val BASE_URL = "http://192.168.43.144/kotlin/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

}

object GetPostsClient {

    private val AUTH = "Basic "+ Base64.encodeToString("mohsinziabutt:123456".toByteArray(), Base64.NO_WRAP)

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", AUTH)
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

}