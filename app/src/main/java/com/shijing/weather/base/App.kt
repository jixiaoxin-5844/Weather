package com.shijing.weather.base

import com.june.basicslibrary.base.BaseApp
import com.shijing.weather.base.ActivityLifeCycleE
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import dagger.hilt.android.HiltAndroidApp

/**
 * author : Hyt
 * time : 2020/09/28
 * version : 1.0
 *
 */
@HiltAndroidApp
class App : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        initUM()

        //统计app使用时间
        val activityLifeCycle = ActivityLifeCycleE()
        registerActivityLifecycleCallbacks(activityLifeCycle)
    }

    private fun initUM() {
       // UMConfigure.setLogEnabled(true) //日志，默认关闭，正式版请关闭
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null)

        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)

    }
}