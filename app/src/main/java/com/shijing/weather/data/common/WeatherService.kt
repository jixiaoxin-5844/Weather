package com.shijing.weather.data.common


import com.shijing.weather.bean.CityInfoBean
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


    //城市信息搜索，获取城市id用于天气查询
    @GET("lookup?")
    suspend fun getLocationId(@Query("location")location: String,
                              @Query("key")key: String):CityInfoBean


    //实况天气
    @GET("now?")
    suspend fun getNow(@Query("location")location: String,
                       @Query("key")key: String) : WeatherNowBean

    //三天预报
    @GET("3d?")
    suspend fun getDaily(@Query("location")location: String,
                       @Query("key")key: String) : WeatherDailyBean

    //未来24小时逐小时预报
    @GET("7d?")
    suspend fun getSevenDaily(@Query("location")location: String,
                         @Query("key")key: String) : WeatherDailyBean

    //七天预报
    @GET("24h?")
    suspend fun getHourly(@Query("location")location: String,
                          @Query("key")key: String) : WeatherHourlyBean

}