package com.june.basicslibrary.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.june.basicslibrary.R
import kotlinx.android.synthetic.main.dialog_await.*

/**
 * author : Hyt
 * time : 2020/09/06
 * version : 1.0
 *
 */
class AwaitDialog0(context0: Context) : Dialog(context0){

    private val defaultAssetsFolder = "load0.json"
    private var imageAssetsFolder: String = defaultAssetsFolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(R.color.transparent)
        setContentView(R.layout.dialog_await)
        initView()
        // setCanceledOnTouchOutside(false)
    }


    private fun initView() {
        lottieAnimationView.imageAssetsFolder = "images"
        lottieAnimationView.setAnimation(imageAssetsFolder)
        lottieAnimationView.playAnimation()
    }

    /**
     * 设置dialog占满原来的布局
     */
    private fun changeDialogStyle() {
        val window: Window? = window
        window?.attributes?.run {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            width = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }

    //****************** 暴露外部的 fun

    //切换动画
    fun setImageAssetsFolder(imageAssetsFolder : String){
        this.imageAssetsFolder = imageAssetsFolder
    }
}