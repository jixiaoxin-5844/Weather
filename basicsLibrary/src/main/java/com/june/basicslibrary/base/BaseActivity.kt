package com.june.basicslibrary.base

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.june.basicslibrary.utils.StatusBarUtils

/**
 * Hilt 不让使用抽象类 我干
 * */

open class BaseActivity : AppCompatActivity() {

   // private lateinit var mFactory: ViewModelProvider.Factory
    open var barColor: Int = Color.parseColor("#ffffff")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils().setColor(this, barColor) //设置状态栏颜色

    }

    override fun onResume() {
        super.onResume()
        // MobclickAgent.onResume(this)  //Auto模式不需要手动调用
    }

    override fun onPause() {
        super.onPause()
        // MobclickAgent.onPause(this)
    }


/*    protected fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(
            this.applicationContext as BaseApp,
            getAppFactory(this)
        )
    }

    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application: Application = checkApplication(activity)
        if (!this::mFactory.isInitialized) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }*/

}