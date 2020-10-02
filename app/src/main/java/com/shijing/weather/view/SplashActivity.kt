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

        if (AppDataKv.getFirstOpenApp()) {
            AppDataKv.setFirstOpenApp(false)

            //注册通知渠道 -> 消息渠道 ...
            createChannel()

        }
        startActivity(Intent(this,MainActivity::class.java))
        finish()

    }

    /**
     * 创建通知渠道的这部分代码,可以写在程序的任何位置，只需要保证在通知弹出之前调用就可以了。
     * 并且创建通知渠道的代码只在第一次执行的时候才会创建，
     * 以后每次执行创建代码系统会检测到该通知渠道已经存在了，
     * 因此不会重复创建，也并不会影响任何效率。
     *
     * */
    private fun createChannel() {

        val sendMessageId = getString(R.string.channel_sendMessageId)
        val sendMessage = getString(R.string.channel_sendMessage)
        val sendDescriptionMessage = getString(R.string.description_sendMessage)

        //API大于等于26才需要创建渠道
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_DEFAULT  //通知重要性等级
           // val importanceLow = NotificationManager.IMPORTANCE_LOW  //通知重要性等级

            NotificationUtils.createChannel(
                this,
                sendMessageId,
                sendMessage,
                importance,
                sendDescriptionMessage
            )
        }
    }


}