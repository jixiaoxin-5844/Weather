package com.shijing.weatherlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.june.basicslibrary.utils.ReportItemDecoration
import com.shijing.weatherlibrary.adapter.Weather2Adapter
import com.shijing.weatherlibrary.bean.Daily
import kotlinx.android.synthetic.main.viewgroup_weather2.view.*

/**
 * author : Hyt
 * time : 2020/10/02
 * version : 1.0
 *
 */
class WeatherLayout2: ConstraintLayout {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.viewgroup_weather2, this, true)


        weather2_recyclerview.addItemDecoration(ReportItemDecoration(context, 8))
    }

    //逐小时预报
    fun setRecycler0(daily: MutableList<Daily>){

        val weather2Adapter = Weather2Adapter(R.layout.item_weather2, daily)
        weather2_recyclerview.run {
            layoutManager =     // LinearLayoutManager(context)
                GridLayoutManager(context,1).apply {
                orientation = GridLayoutManager.VERTICAL
            }

            adapter = weather2Adapter
        }
    }

}