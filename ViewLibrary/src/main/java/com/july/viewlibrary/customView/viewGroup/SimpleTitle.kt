package com.july.viewlibrary.customView.viewGroup

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.july.viewlibrary.R
import kotlinx.android.synthetic.main.simple_title.view.*

/**
 * author : Hyt
 * time : 2020/07/26
 * version : 1.0
 * 一个简单的组合控件
 * 简单的标题栏，只有返回按钮和title, title在中间
 */
class SimpleTitle: ConstraintLayout {

    private var titleName = "首页"

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        val mTypeArray =
            context.obtainStyledAttributes(attrs, R.styleable.SimpleTitle)

        titleName = mTypeArray.getString(R.styleable.SimpleTitle_titleName).toString()

        mTypeArray.recycle()
        initView(context)
    }

    private fun initView(context: Context){
        LayoutInflater.from(context).inflate(R.layout.simple_title,this,true)

        // 默认关闭本Activity    ->   Fragment中使用 ????
        simpleTitleBack.setOnClickListener{
            try {
                (context as Activity).finish()
            } catch (e: Exception) {
            }
        }
        simpleTitleText.text = titleName
    }

    fun setBackListener(onClickListener: OnClickListener){
        simpleTitleBack.setOnClickListener(onClickListener)
    }



}