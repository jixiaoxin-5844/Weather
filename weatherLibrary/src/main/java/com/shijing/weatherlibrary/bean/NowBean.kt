package com.shijing.weatherlibrary.bean

/**
 * author : Hyt
 * time : 2020/10/01
 * version : 1.0
 * 实况天气的 可以用到的数据 的 数据类
 */
data class NowBean(
    val temp : String,    //温度         ->17
    val text : String,     //天气状况文字  ->晴
    val feelsLike : String //体感温度     ->20
)