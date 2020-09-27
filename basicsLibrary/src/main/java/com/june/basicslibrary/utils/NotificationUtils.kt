package com.june.basicslibrary.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build

/**
 * author : Hyt
 * time : 2020/08/22
 * version : 1.0
 *
 */
object NotificationUtils {

    /**
     * 创建通知渠道 -> 标准模板
     * @param channelId    渠道ID
     * @param channelName  渠道名字
     * @param importance   通知重要等级
     * @param description  通知描述
     * @param low          低重要性 -> 表现为 无提示音,无震动,无闪烁灯
     * */
    fun createChannel(
        context: Context,
        channelId: String, channelName: String,
        importance: Int, description: String,low : Boolean = false
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                this.description = description
            }
            if(low){
                with(channel){
                    enableVibration(false) //不震动
                }
            }
            val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}