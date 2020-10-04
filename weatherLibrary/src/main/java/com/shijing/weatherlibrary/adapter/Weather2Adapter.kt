package com.shijing.weatherlibrary.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shijing.weatherlibrary.R
import com.shijing.weatherlibrary.bean.Daily

/**
 * author : Hyt
 * time : 2020/10/02
 * version : 1.0
 *
 */
class Weather2Adapter (layoutResId: Int,data: MutableList<Daily>) :
    BaseQuickAdapter<Daily, BaseViewHolder>(layoutResId,data) {

    override fun convert(holder: BaseViewHolder, item: Daily) {
        //时间
        val fxTime = data[holder.layoutPosition].fxDate
        holder.setText(R.id.itemWeather2_date,fxTime)

        //降水概率无此数据

        val tempMax = data[holder.layoutPosition].tempMax
        val tempMin = data[holder.layoutPosition].tempMin
        //最高最低气温
        holder.setText(R.id.itemWeather2_temp,"${tempMax}°/${tempMin}°")


    }

}