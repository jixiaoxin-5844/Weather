package com.june.basicslibrary.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.june.basicslibrary.R
import com.june.basicslibrary.intefaceA.UpdateDialogInterface
import kotlinx.android.synthetic.main.dialog_update.*

/**
 * author : Hyt
 * time : 2020/09/06
 * version : 1.0
 * @param path 下载地址
 */
class UpdateDialog(context: Context):Dialog(context) {

    private lateinit var updateDialogInterface: UpdateDialogInterface

    private var contentText = "修复已知问题，优化用户体验"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(R.color.transparent)
        setContentView(R.layout.dialog_update)
        changeDialogStyle()
        //设置点击外部无法关闭
        setCanceledOnTouchOutside(false)
        initView()

    }


    private fun initView() {

        //文字描述内容 -> 更新内容
        updateDialog_content.text = contentText

        updateDialog_Tv0.setOnClickListener {
            //取消
            updateDialogInterface.cancel()
        }
        updateDialog_Tv1.setOnClickListener {
            //更新
            updateDialogInterface.confirm()
        }

    }

    fun dismiss(context: Context) {
        try {
            if (context is Activity && context is FragmentActivity) {
                if (context.isFinishing) {
                    dismiss()
                    return
                }
            }
            if (isShowing) {
                dismiss()
                return
            }
        } catch (e: Exception) {
           dismiss()
        }
    }

    /**
     * 设置提醒文字
     * @param content 更新内容
     * */
    fun setContent(content : String){
        this.contentText = content
    }


    fun setClickListener(update: UpdateDialogInterface){
        this.updateDialogInterface = update
    }

    /**
     * 设置dialog占满原来的布局
     */
    private fun changeDialogStyle() {
        val window: Window? = window
        window?.attributes?.run {
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            width = ViewGroup.LayoutParams.MATCH_PARENT
            //gravity = Gravity.BOTTOM
        }
    }




}