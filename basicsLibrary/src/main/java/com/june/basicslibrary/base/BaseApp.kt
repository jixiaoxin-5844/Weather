package com.june.basicslibrary.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.hjq.toast.ToastUtils
import com.tencent.mmkv.MMKV
import org.litepal.LitePal
import kotlin.properties.Delegates

/**
 * author : Hyt
 * time : 2020/07/11
 * version : 1.0
 *
 */
open class BaseApp : Application() {

    companion object {
        @JvmStatic
        var CONTEXT: Context by Delegates.notNull()
        var kv: MMKV by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        MMKV.initialize(this)  // MM KV
        kv = MMKV.defaultMMKV()  //获取实例对象
        LitePal.initialize(this)  //初始化 LitePal
        ToastUtils.init(this) //吐司

    }




}