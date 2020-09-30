package com.shijing.weatherlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * author : Hyt
 * time : 2020/09/28
 * version : 1.0
 *
 */
class WeatherLayout0 : ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.viewgroup_weather0,this,true)


    }
}