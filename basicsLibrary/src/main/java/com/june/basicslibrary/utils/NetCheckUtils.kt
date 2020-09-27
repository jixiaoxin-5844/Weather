package com.june.basicslibrary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi


/**
 * author : Hyt
 * time : 2020/08/04
 * version : 1.0
 *
 */
@RequiresApi(Build.VERSION_CODES.M)
object NetCheckUtils {


    /**
     * 判断网络是否连接
     */
    fun isConnected(context: Context): Boolean {
        val systemService =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = systemService.activeNetwork ?: return false
        val capabilities = systemService.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }


    /**
     * 判断网络类型：移动网络
     */
    fun isMobile(context: Context): Boolean {
        val systemService =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = systemService.activeNetwork ?: return false
        val cap = systemService.getNetworkCapabilities(activeNetwork) ?: return false
        return cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    /**
     * 判断网络类型：Wi-Fi类型
     */
    fun isWifi(context: Context): Boolean {
        val systemService =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = systemService.activeNetwork ?: return false
        val cap = systemService.getNetworkCapabilities(activeNetwork) ?: return false
        return cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }


}