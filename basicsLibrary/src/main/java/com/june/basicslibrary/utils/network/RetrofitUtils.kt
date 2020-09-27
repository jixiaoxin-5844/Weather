package com.june.basicslibrary.utils.network

import com.june.basicslibrary.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * author : Hyt
 * time : 2020/07/18
 * version : 1.0
 *
 */
object RetrofitUtils {
    //companion object {

        fun getHttp(baseUrl: String): Retrofit {
            if(BuildConfig.DEBUG){
                val logger = HttpLoggingInterceptor.Logger
                logger.DEFAULT
                val newBuilder = OkHttpClient().newBuilder()
            }

            return Retrofit.Builder()
                .baseUrl(baseUrl)

                //.addCallAdapterFactory()
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

   // }
}