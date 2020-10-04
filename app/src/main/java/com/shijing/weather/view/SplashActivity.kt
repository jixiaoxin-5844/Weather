package com.shijing.weather.view

import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.june.basicslibrary.base.BaseActivity
import com.june.basicslibrary.utils.NotificationUtils
import com.shijing.weather.R
import com.shijing.weather.data.storage.AppDataKv
import com.umeng.analytics.MobclickAgent

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {


        startActivity(Intent(this,MainActivity::class.java))
        finish()

    }




}