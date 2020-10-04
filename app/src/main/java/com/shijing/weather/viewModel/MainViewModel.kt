package com.shijing.weather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.june.basicslibrary.model.RetrofitUtils
import com.june.basicslibrary.utils.L
import com.shijing.weather.BuildConfig
import com.shijing.weather.data.api.BaseAction
import com.shijing.weather.data.common.WeatherService
import com.shijing.weather.data.storage.WeatherDataKv
import com.shijing.weatherlibrary.bean.*
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

    private val locationApi = RetrofitUtils.getHttp(BaseAction.WeatherCity)
        .create(WeatherService::class.java)

    var nowBean = MutableLiveData<NowBean>()
    var dailyBean = MutableLiveData<DailyBean>()
    var hourlyBean = MutableLiveData<MutableList<Hourly>>()
    var sevenDailyBean = MutableLiveData<MutableList<Daily>>()

    var locationId = MutableLiveData(WeatherDataKv.getLocationId())

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
                bean.updateTime,
                bean.now!!.temp,
                bean.now.text,
                bean.now.feelsLike
            )
            this@MainViewModel.nowBean.postValue(nowBean)
        }
    }

    /**
     *  获取未来三天预报天气并转换为对应的 bean
     *  当前只拿当天的数据
     *  @param location 要查询的城市
     * */
    fun getDailyData(location: String = "101010100") {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherDailyBean =
                async { weatherApi.getDaily(location, BuildConfig.WeatherKey) }
            val bean = weatherDailyBean.await()

            val daily = bean.daily?.get(0)

            val dailyBean = DailyBean(
                daily!!.tempMax, daily.tempMin, daily.uvIndex.toInt(),
                daily.sunrise, daily.sunset, daily.pressure, daily.humidity, daily.windScaleDay
            )

            this@MainViewModel.dailyBean.postValue(dailyBean)

        }
    }

    /**
     *  获取24小时逐小时 天气并转换为对应的 bean
     *  @param location 要查询的城市编码信息
     *  或者输入以逗号分隔的经度/纬度坐标
     * */
    fun getHourlyData(location: String = "101010100") {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherHourlyBean =
                async { weatherApi.getHourly(location, BuildConfig.WeatherKey) }
            val bean = weatherHourlyBean.await()
            //需要的数据是第 0  3   6   9   12
            val listOf = listOf(0, 3, 6, 9, 12)
            val list = mutableListOf<Hourly>()
            for (i in listOf) {
                list.add(bean.hourly[i])
            }
            this@MainViewModel.hourlyBean.postValue(list)
        }
    }

    /**
     *  获取七天 天气并转换为对应的 bean
     *  @param location 要查询的城市编码信息
     *  或者输入以逗号分隔的经度/纬度坐标
     * */
    fun getSevenDaily(location: String = "101010100") {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherDailyBean =
                async { weatherApi.getSevenDaily(location, BuildConfig.WeatherKey) }
            val bean = weatherDailyBean.await()
            val daily = bean.daily
            if (daily != null) {
                sevenDailyBean.postValue(daily as MutableList<Daily>?)
            }
        }
    }

    /**
    *
    * */
    fun getLocationInfo(location: String = "beijing") {
        viewModelScope.launch(Dispatchers.IO) {
            val cityInfoBean =
                async { locationApi.getLocationId(location, BuildConfig.WeatherKey) }
            val bean = cityInfoBean.await()
            bean.location?.get(0).let {
                val id = it?.id
                if(id!=null){
                    WeatherDataKv.setLocationId(id)//存储id
                    locationId.postValue(id)
                    L.d("MainViewModel","当前LocationId:${id}")
                }
            }

        }
    }

}