package com.shijing.weatherlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.june.basicslibrary.utils.ReportItemDecoration
import com.shijing.weatherlibrary.adapter.HourlyAdapter
import com.shijing.weatherlibrary.bean.DailyBean
import com.shijing.weatherlibrary.bean.Hourly
import com.shijing.weatherlibrary.bean.NowBean
import com.shijing.weatherlibrary.interfaceA.OnImgClickListener
import kotlinx.android.synthetic.main.viewgroup_weather0.view.*

/**
 * author : Hyt
 * time : 2020/09/28
 * version : 1.0
 *
 */
class WeatherLayout0 : ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context, attrs)
    }

    private lateinit var listener: OnImgClickListener

    fun setOnImgClickListener(listener: OnImgClickListener){
        this.listener = listener
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.viewgroup_weather0,this,true)

        //定位
        weather0_location.setOnClickListener {
            listener.click()
        }

        weather0_Recycler0.addItemDecoration(ReportItemDecoration(context, 12,10))

    /*    weather0_cityName  //城市名
        weather0_time      //几月几号
        weather0_exactTime //当前时间
        weather0_statePic  //天气状态图标
        weather0_tem       //天气温度
        weather0_text0     //天气描述
        weather0_text1     //最高最低温度
        weather0_text2     //体感温度
        weather0_Recycler0 //未来小时天气*/

    }

    /**  设置数据
     *  @param nowBean 数据类
     */
    @SuppressLint("SetTextI18n")
    fun setNowBeanData(nowBean: NowBean){
        weather0_tem.text = "${nowBean.temp}°"
        weather0_text0.text = nowBean.text
        weather0_text2.text = "体感温度${nowBean.feelsLike}°"
        weather0_statePic.setImageResource(R.mipmap.a100)
    }

    /**  设置数据
     *  @param dailyBean 数据类
     */
    @SuppressLint("SetTextI18n")
    fun setDailyBeanData(dailyBean: DailyBean){
        weather0_text1.text = "${dailyBean.tempMax}°/${dailyBean.tempMin}°"

    }

    //时间
    fun setDate(date:String){
        weather0_time.text = date
    }

    //城市名
    fun setCityName(cityName:String){
        weather0_cityName.text = cityName
    }

    //定位图标
    fun setLocationPic(resId:Int){
        weather0_location.setImageResource(resId)
    }

    //经纬度
    @SuppressLint("SetTextI18n")
    fun setLocationText(latitudeAndLongitude: String){
        weather0_locationText.text = "经纬度:${latitudeAndLongitude}"
    }

    //设置经纬度text可见，在有定位权限的时候
    fun setLocationTextVisible(){
        weather0_locationText.visibility = VISIBLE
    }

    //逐小时预报
    fun setRecycler0(hourly: MutableList<Hourly>){
        val hourlyAdapter = HourlyAdapter(R.layout.item_hourly, hourly)
        weather0_Recycler0.run {
            layoutManager = GridLayoutManager(context,1).apply {
                orientation = GridLayoutManager.HORIZONTAL
            }

            adapter = hourlyAdapter
        }
    }

}