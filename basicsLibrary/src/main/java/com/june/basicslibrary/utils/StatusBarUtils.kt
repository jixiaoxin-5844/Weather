package com.june.basicslibrary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.core.graphics.ColorUtils


class StatusBarUtils {

    companion object{

    }

    //获取状态栏高度
    fun getHeight(context: Context):Int{
        var statusBarHeight = 0
        try {
            val resourceId = context.resources.getIdentifier(
                "status_bar_height", "dimen",
                "android")

            if(resourceId > 0){
                statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusBarHeight
    }

    //设置状态栏颜色
    fun setColor(@NonNull window: Window, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = color
            setTextDark(window, !isDarkColor(color))
        }
    }

    fun setColor(context: Context?, @ColorInt color: Int) {
        if (context is Activity) {
            setColor(context.window, color)
        }
    }

    //设置状态栏文字颜色 (深色模式和浅色模式)仅支持6.0以上  API >=23
    fun setTextDark(window: Window, isDark: Boolean): Unit {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decorView = window.decorView
            val systemUiVisibility = decorView.systemUiVisibility
            if (isDark) {
                decorView.systemUiVisibility =
                    systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility =
                    systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
    }

    fun setTextDark(context: Context?, isDark: Boolean) {
        if (context is Activity) {
            setTextDark(context.window, isDark)
        }
    }

    /*判断颜色深浅
    * 为了根据状态栏背景颜色的深浅而自动设置文字的颜色
    * */
    fun isDarkColor(@ColorInt color: Int): Boolean {
        return ColorUtils.calculateLuminance(color) < 0.5
    }

    fun setTransparent(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun setTransparent(context: Context?) {
        if (context is Activity) {
            setTransparent(context.window)
        }
    }

    //设置全屏，不显示状态栏
    fun setStatusBar(activity: Activity){
        val decorView = activity.window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

    }


    //设置全屏透明，显示状态栏->适用于图片全屏展示和全屏布局背景
    fun setStatusBarTransparency(activity: Activity):StatusBarUtils{
        val decorView = activity.window.decorView
        val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = option
        activity.window.statusBarColor = Color.TRANSPARENT
        return this
    }

    //设置不显示底部导航栏
    fun setNavigationBarStatus(activity: Activity):StatusBarUtils{
        val decorView = activity.window.decorView
        val option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = option
        return this
    }

}