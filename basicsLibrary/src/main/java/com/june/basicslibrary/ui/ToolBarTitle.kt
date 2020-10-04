package com.june.basicslibrary.ui

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.june.basicslibrary.R
import com.june.basicslibrary.intefaceA.OnImgClickListener
import kotlinx.android.synthetic.main.toolbar_title.view.*


/**
 * author : Hyt
 * time : 2020/09/26
 * version : 1.0
 *                        返回按钮   文字    图标 -> 图标未设置不显示
 */
class ToolBarTitle  : ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_title,this,true)

        val mTypeArray =
            context.obtainStyledAttributes(attrs, R.styleable.ToolBarTitle)

        val title = mTypeArray.getString(R.styleable.ToolBarTitle_title)
        val src = mTypeArray.getResourceId(R.styleable.ToolBarTitle_btnSrc,0)

        mTypeArray.recycle()

        if(src != 0){
            with(toolBar_RightImg0){
                setImageResource(src)
                visibility = VISIBLE
                setOnClickListener {
                    if(this@ToolBarTitle::listener.isInitialized){
                        listener.click()
                    }
                }
            }

        }

        title?.let {
            toolBar_TitleText.text = it
        }

        toolBar_Back.setOnClickListener{
            try {
                (context as Activity).finish()
            } catch (e: Exception) {
            }
        }
    }

    // ***************************
    private lateinit var listener : OnImgClickListener

    // fun
    fun setClickListener(listener : () -> Unit){
        this.listener = object : OnImgClickListener{
            override fun click() {
                listener.invoke()
            }
        }

    }


}