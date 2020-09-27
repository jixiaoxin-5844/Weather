package com.june.basicslibrary.model

import com.june.basicslibrary.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * author : Hyt
 * time : 2020/07/18
 * version : 1.0
 *
 */
object RetrofitUtils {

    private const val TIME_OUT = 4L


    fun getHttp(baseUrl: String): Retrofit {

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(MyInterceptor())
            .build()

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }

        val okHttpClient0 = OkHttpClient()
            .newBuilder()
            .addInterceptor(logging)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            // .addCallAdapterFactory(MyCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            // .client(okHttpClient)
            .client(okHttpClient0)
            .build()
    }


}