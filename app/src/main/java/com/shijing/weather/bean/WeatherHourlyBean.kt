package com.shijing.weather.bean

import com.shijing.weatherlibrary.bean.Hourly

/**
 * author : Hyt
 * time : 2020/10/02
 * version : 1.0
 *
 */
data class WeatherHourlyBean(
    val code: String?,
    val fxLink: String?,
    val hourly: List<Hourly>,
    val refer: Refer2?,
    val updateTime: String?
)



data class Refer2(
    val license: List<String>?,
    val sources: List<String>?
)