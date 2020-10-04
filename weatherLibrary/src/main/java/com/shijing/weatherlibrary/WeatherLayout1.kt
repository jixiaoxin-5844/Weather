package com.shijing.weatherlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.viewgroup_weather1.view.*

/**
 * author : Hyt
 * time : 2020/10/02
 * version : 1.0
 * 显示降水概率和紫外线指数
 */
class WeatherLayout1 : ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.viewgroup_weather1, this, true)

        weather1_rainfallText2  //降雨概率
        weather1_urText         //紫外线

    }

    /**
     * @param rainfall 降雨概率
     * */
    @SuppressLint("SetTextI18n")
    fun setRainfall(rainfall: String){
        weather1_rainfallText2.text = "${rainfall}%"  //降雨概率
    }

    /**
     * @param uv 紫外线指数
     *  紫外线指数 ->
     *  0-2 低
     *  3-5 中等
     *  6-7 高
     *  8-10 甚高
     *  >=11 极高
     * */
    fun setUv(uv: Int) {
        weather1_urText.text = //紫外线
            when (uv) {
                in 0..2 -> "低($uv)"
                in 3..5 -> "中等($uv)"
                6,7 ->   "高($uv)"
                in 8..10 -> "甚高($uv)"
                else->  "极高($uv)"
            }
    }

}