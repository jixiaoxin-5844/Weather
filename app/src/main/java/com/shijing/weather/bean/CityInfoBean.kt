package com.shijing.weather.bean

/**
 * author : Hyt
 * time : 2020/10/03
 * version : 1.0
 *
 */
data class CityInfoBean(
    val location: List<Location>?,
    val status: String?
)

data class Location(
    val adm1: String?,
    val adm2: String?,
    val country: String?,
    val fxLink: String?,
    val id: String?,
    val isDst: String?,
    val lat: String?,
    val lon: String?,
    val name: String?,
    val rank: String?,
    val type: String?,
    val tz: String?,
    val utcOffset: String?
)