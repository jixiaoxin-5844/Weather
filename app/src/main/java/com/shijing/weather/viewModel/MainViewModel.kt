package com.shijing.weather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.june.basicslibrary.model.RetrofitUtils
import com.shijing.weather.BuildConfig
import com.shijing.weather.data.api.BaseAction
import com.shijing.weather.data.common.WeatherService
import com.shijing.weatherlibrary.bean.DailyBean
import com.shijing.weatherlibrary.bean.Hourly
import com.shijing.weatherlibrary.bean.NowBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * author : Hyt
 * time : 2020/10/01
 * version : 1.0
 *
 */
class MainViewModel : ViewModel() {

    private val weatherApi = RetrofitUtils.getHttp(BaseAction.WeatherPath)
        .create(WeatherService::class.java)

    var nowBean = MutableLiveData<NowBean>()
    var dailyBean = MutableLiveData<DailyBean>()
    var hourlyBean = MutableLiveData<MutableList<Hourly>>()

    /**
     *  获取实时天气并转换为对应的 bean
     *  @param location 要查询的城市
     * */
    fun getNowData(location: String = "101010100") {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherNowBean =
                async { weatherApi.getNow(location, BuildConfig.WeatherKey) }
            val bean = weatherNowBean.await()

            val nowBean = NowBean(
                bean.now!!.temp,
                bean.now.text,
                bean.now.feelsLike
            )
            this@MainViewModel.nowBean.postValue(nowBean)
        }
    }

    /**
     *  获取未来三天预报天气并转换为对应的 bean
     *  @param location 要查询的城市
     * */
    fun getDailyData(location: String = "101010100") {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherDailyBean =
                async { weatherApi.getDaily(location, BuildConfig.WeatherKey) }
            val bean = weatherDailyBean.await()

            val daily = bean.daily?.get(0)
            val dailyBean = DailyBean(daily!!.tempMax, daily.tempMin)

            this@MainViewModel.dailyBean.postValue(dailyBean)

        }
    }

    /**
     *  获取实时天气并转换为对应的 bean
     *  @param location 要查询的城市
     * */
    fun getHourlyData(location: String = "101010100") {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherHourlyBean =
                async { weatherApi.getHourly(location, BuildConfig.WeatherKey) }
            val bean = weatherHourlyBean.await()
            //需要的数据是第 0  3   6   9   12
            val listOf = listOf(0, 3, 6, 9, 12)
            val list = mutableListOf<Hourly>()
            for (i in listOf.indices){
                list.add(bean.hourly[i])
            }
            this@MainViewModel.hourlyBean.postValue(list)
        }
    }

}