package com.shijing.weather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shijing.weather.data.storage.WeatherDataKv

/**
 * author : Hyt
 * time : 2020/10/04
 * version : 1.0
 *
 */
class SettingViewModel: ViewModel() {

    var cityName = MutableLiveData(WeatherDataKv.getCityName())

}