package com.shijing.weather.data.common

import com.shijing.weather.bean.VersionBean
import retrofit2.Call
import retrofit2.http.GET

/**
 * author : Hyt
 * time : 2020/10/04
 * version : 1.0
 *
 */
interface OssService {

    @GET("json/WeatherDebugVersion.json")
    fun getVersion():Call<VersionBean>


}