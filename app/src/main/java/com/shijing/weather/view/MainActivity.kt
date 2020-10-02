package com.shijing.weather.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.june.basicslibrary.base.BaseActivity
import com.june.basicslibrary.utils.TimeUtils
import com.shijing.weather.R
import com.shijing.weather.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()

        //获取数据
        getData()
        startObserve()
    }

    private fun initData() {
        //定位，判断有无定位权限展示不同图标   !!!
        WeatherLayout0.setLocationPic(com.shijing.weatherlibrary.R.mipmap.location)

        //城市名与时间                        !!!
        WeatherLayout0.setLocationAndDate("北京", TimeUtils.getExactTime2())

    }

    private fun getData() {

        viewModel.getNowData()
        viewModel.getDailyData()
        viewModel.getHourlyData()

    }

    private fun startObserve() {

        viewModel.nowBean.observe(this, {
            WeatherLayout0.setNowBeanData(it)
        })

        viewModel.dailyBean.observe(this, {
            WeatherLayout0.setDailyBeanData(it)
        })

        viewModel.hourlyBean.observe(this,{
            WeatherLayout0.setRecycler0(it)
        })

    }


}