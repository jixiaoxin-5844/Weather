package com.june.basicslibrary.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.june.basicslibrary.R
import kotlinx.android.synthetic.main.dialog_await.*


/**
 * author : Hyt
 * time : 2020/08/26
 * version : 2.0
 * 使用方式0 -> 使用默认动画文件 (load0.json)
 *         AwaitDialog.show(this)
 *         AwaitDialog.dismiss(this)
 *
 *  使用方式1 -> 自己设计动画文件
 *        val awaitDialog = AwaitDialog(this).apply {
 *           this.setImageAssetsFolder("litterHorse.json")
 *            this.show()
 *          } //显示
 *         awaitDialog.dismiss() //关闭
 */

/**
 * bug  创建实例时用到了之前的activity ，导致崩溃
 * 请暂时不要使用此类 **************************************************************************************
 * */
class AwaitDialog(context0: Context) : Dialog(context0) {

    private val defaultAssetsFolder = "load0.json"
    private var imageAssetsFolder: String = defaultAssetsFolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(R.color.transparent)
        setContentView(R.layout.dialog_await)
        initView()
       // setCanceledOnTouchOutside(false)
    }

    companion object {
        private lateinit var awaitDialog: AwaitDialog
        private lateinit var contextName : String

        /**
         * 彩虹马,冲冲冲
         */
        fun show(context: Context) {
            getInstance(context).show()
        }

        /**
         *  彩虹马,撤退
         * */
        fun dismiss(context: Context) {
            try {
                if (context is Activity) {
                    if (context.isFinishing) {
                        getInstance(context).dismiss()
                        return
                    }
                }
                if (getInstance(context).isShowing) {
                    getInstance(context).dismiss()
                    return
                }
            } catch (e: Exception) {
                getInstance(context).dismiss()
            }
        }

        /**
         * 创建 -> 同一个activity生命周期内创建的都是同一个 Dialog对象
         */
        private fun getInstance(context: Context): AwaitDialog {
            if(!this::contextName.isInitialized){
                contextName = context.javaClass.name
                if(!this::awaitDialog.isInitialized){
                    awaitDialog = AwaitDialog(context)
                }
                return awaitDialog
            }else{
                //判断两个context是否同一个
                return if (contextName != context.javaClass.name){
                    contextName = context.javaClass.name
                    awaitDialog = AwaitDialog(context)
                    awaitDialog
                }else{
                    awaitDialog
                }
            }
        }
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