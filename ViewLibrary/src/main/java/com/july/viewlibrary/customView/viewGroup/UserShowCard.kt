package com.july.viewlibrary.customView.viewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.july.viewlibrary.R
import kotlinx.android.synthetic.main.usershow_card.view.*

/**
 * author : Hyt
 * time : 2020/08/16
 * version : 1.0
 * 展示用户信息
 */
class UserShowCard :ConstraintLayout{
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.usershow_card, this, true)
        val mTypeArray =
            context.obtainStyledAttributes(attrs, R.styleable.UserShowCard)

        val headPic = mTypeArray.getResourceId(R.styleable.UserShowCard_UserShowCard_headPic, -1)

        if(headPic != -1){
            userShow_HeadPortrait.setImageResource(headPic)
        }

        mTypeArray.recycle()
    }
}