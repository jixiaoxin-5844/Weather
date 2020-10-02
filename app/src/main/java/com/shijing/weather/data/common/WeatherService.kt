package com.shijing.weather.data.common


import com.shijing.weather.bean.WeatherDailyBean
import com.shijing.weather.bean.WeatherHourlyBean
import com.shijing.weather.bean.WeatherNowBean
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * author : Hyt
 * time : 2020/09/21
 * version : 1.0
 *
 */
interface WeatherService {

    //实况天气
    @GET("now?")
    suspend fun getNow(@Query("location")location: String,
                       @Query("key")key: String) : WeatherNowBean

    //实况天气
    @GET("3d?")
    suspend fun getDaily(@Query("location")location: String,
                       @Query("key")key: String) : WeatherDailyBean

    //未来24小时逐小时预报
    @GET("24h?")
    suspend fun getHourly(@Query("location")location: String,
                         @Query("key")key: String) : WeatherHourlyBean


}