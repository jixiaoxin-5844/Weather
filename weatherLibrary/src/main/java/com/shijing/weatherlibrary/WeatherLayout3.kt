package com.shijing.weatherlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.shijing.weatherlibrary.bean.DailyBean
import kotlinx.android.synthetic.main.item_weather3.view.*
import kotlinx.android.synthetic.main.viewgroup_weather3.view.*

/**
 * author : Hyt
 * time : 2020/10/03
 * version : 1.0
 *
 */
class WeatherLayout3: ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.viewgroup_weather3, this, true)

        itemWeather0.itemWeather3_img.setImageResource(R.mipmap.sunrise)
        itemWeather0.itemWeather3_text0.text = "日出"

        itemWeather1.itemWeather3_img.setImageResource(R.mipmap.sunset)
        itemWeather1.itemWeather3_text0.text = "日落"

        itemWeather2.itemWeather3_img.setImageResource(R.mipmap.pressure)
        itemWeather2.itemWeather3_text0.text = "大气压强"

        itemWeather3.itemWeather3_img.setImageResource(R.mipmap.humidity)
        itemWeather3.itemWeather3_text0.text = "湿度"

        itemWeather4.itemWeather3_img.setImageResource(R.mipmap.wind)
        itemWeather4.itemWeather3_text0.text = "风"
        itemWeather4.itemWeather3_bottomLine.visibility = GONE

    }

    /**  设置数据
     *  @param dailyBean 数据类
     */
    @SuppressLint("SetTextI18n")
    fun setDailyBeanData(dailyBean: DailyBean){
        itemWeather0.itemWeather3_text1.text = dailyBean.sunrise
        itemWeather1.itemWeather3_text1.text = dailyBean.sunset
        itemWeather2.itemWeather3_text1.text = "${dailyBean.pressure}百帕"
        itemWeather3.itemWeather3_text1.text = "${dailyBean.humidity}%"
        itemWeather4.itemWeather3_text1.text = "${dailyBean.windScaleDay}级"

    }

}