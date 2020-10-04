package com.shijing.weather.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.june.basicslibrary.utils.L
import com.shijing.weather.data.storage.WeatherDataKv
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.HotCity
import com.zaaach.citypicker.model.LocatedCity

/**
 * author : Hyt
 * time : 2020/10/04
 * version : 1.0
 *
 */
class WeatherUtils {
    companion object{

        fun openSelectCity(context: FragmentActivity,listener: OnPickListener) {
            //热门城市
            val list = ArrayList<HotCity>().apply {
                add(HotCity("北京", "北京", "101010100"))
                add(HotCity("上海", "上海", "101020100"))
                add(HotCity("深圳", "广东", "101280601"))
                add(HotCity("杭州", "浙江", "101210101"))
            }

            CityPicker.from(context)
                .enableAnimation(true)
                .setLocatedCity(LocatedCity("北京", "北京", "101010100"))
                .setHotCities(list)
                .setOnPickListener(listener).show()

        }

    }
}