package com.shijing.weather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.june.basicslibrary.model.CallAdapter
import com.june.basicslibrary.model.RetrofitUtils
import com.shijing.weather.BuildConfig
import com.shijing.weather.bean.VersionBean
import com.shijing.weather.data.common.OssService
import com.shijing.weather.data.storage.WeatherDataKv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * author : Hyt
 * time : 2020/10/04
 * version : 1.0
 *
 */
class SettingViewModel: ViewModel() {

    var cityName = MutableLiveData(WeatherDataKv.getCityName())

    var debugVersion = MutableLiveData<VersionBean>()

    //oss
    private val ossApi = RetrofitUtils.getHttp(BuildConfig.OssBasePath)
        .create(OssService::class.java)


    /**
     * 获取最新的debug版本
     * */
    fun getDebugVersion(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = CallAdapter
                .execute(ossApi.getVersion())
            debugVersion.postValue(response?.body())
        }

    }

}