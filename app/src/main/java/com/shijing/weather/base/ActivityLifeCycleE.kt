package com.shijing.weather.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.june.basicslibrary.utils.L
import com.shijing.weather.data.storage.AppDataKv

/**
 * author : Hyt
 * time : 2020/09/26
 * version : 1.0
 *
 *  当前在 (待测试)
 *  start方法表示开始使用 -> 重置当前开始使用时间
 *  Paused 方法表示未使用,离开app ->存储使用时间
 *
 *  如果需要获取当日运行时间，可多存储一个值，每日首次打开清空即可
 */
class ActivityLifeCycleE : Application.ActivityLifecycleCallbacks {
    private val tag = "app_Life"

    /**
     * 每次有Activity启动时的开始时间点
     */
    private var appStartTime = 0L
    //此次存储时间
    private var thisUseTime = 0L



    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        L.d(tag,"onActivityCreated->" + getActivityName(activity))
    }

    override fun onActivityStarted(activity: Activity) {
        L.d(tag,"onActivityStarted->" + getActivityName(activity))
        appStartTime = System.currentTimeMillis()
        thisUseTime = 0L
    }

    override fun onActivityResumed(activity: Activity) {
        L.d(tag,"onActivityResumed->" + getActivityName(activity))
    }

    override fun onActivityPaused(activity: Activity) {
        L.d(tag,"onActivityPaused->" + getActivityName(activity))
        thisUseTime = System.currentTimeMillis() - appStartTime
        saveAllUseTime(thisUseTime)
    }

    override fun onActivityStopped(activity: Activity) {
        L.d(tag,"onActivityStopped->" + getActivityName(activity))
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        L.d(tag,"onActivitySaveInstanceState->" + getActivityName(activity))
    }

    override fun onActivityDestroyed(activity: Activity) {
        L.d(tag,"onActivityDestroyed->" + getActivityName(activity))
    }



    //********************** 分割线

    private fun getActivityName(activity: Activity): String? {
        return activity.javaClass.canonicalName
    }

    /**
     * 保存App总共使用时间
     * 机制是 ->
     * 每一个 activity 显示时，存一个显示时间，不显示时，将(不显示时间 - 开始显示时间) 存起来
     * @param time 此Activity 此次显示时间
     */
    private fun saveAllUseTime(time: Long) {
        val todayTime = AppDataKv.getAppAllUseTime()
        //L.d(TAG,"使用时长Log:" + (todayTime + time))
        AppDataKv.setAppAllUseTime(todayTime + time)
    }

}