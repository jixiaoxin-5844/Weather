package com.shijing.weatherlibrary.bean

/**
 * author : Hyt
 * time : 2020/10/01
 * version : 1.0
 * 三天预报的 可以用到的数据 的 数据类
 */
data class DailyBean (
    val tempMax: String,        //当天最高气温
    val tempMin: String,        //当天最低气温
    val uvIndex: Int,           //紫外线强度指数
    val sunrise: String,        //日出
    val sunset: String,         //日落
    val pressure: String,       //大气压强
    val humidity: String,       //相对湿度
    val windScaleDay: String   //白天风力等级

)