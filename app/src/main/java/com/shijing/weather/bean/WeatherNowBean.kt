package com.shijing.weather.bean

/**
 * author : Hyt
 * time : 2020/09/21
 * version : 1.0
 *
 */
data class WeatherNowBean(
    val code: String?,
    val fxLink: String?,
    val now: Now?,
    val refer: Refer0?,
    val updateTime: String
)

data class Now(
    val cloud: String?,
    val dew: String?,
    val feelsLike: String,
    val humidity: String?,
    val icon: String?,
    val obsTime: String?,
    val precip: String?,
    val pressure: String?,
    val temp: String,
    val text: String,
    val vis: String?,
    val wind360: String?,
    val windDir: String?,
    val windScale: String?,
    val windSpeed: String?
)

data class Refer0(
    val license: List<String>?,
    val sources: List<String>?
)