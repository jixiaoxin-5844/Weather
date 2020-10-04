package com.shijing.weather.bean

import com.shijing.weatherlibrary.bean.Daily

/**
 * author : Hyt
 * time : 2020/10/01
 * version : 1.0
 *
 */
data class WeatherDailyBean(
    val code: String?,
    val daily: List<Daily>?,
    val fxLink: String?,
    val refer: Refer1?,
    val updateTime: String?
)



data class Refer1(
    val license: List<String>?,
    val sources: List<String>?
)