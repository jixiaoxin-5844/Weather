package com.shijing.weatherlibrary.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.june.basicslibrary.utils.Utils
import com.shijing.weatherlibrary.R
import com.shijing.weatherlibrary.bean.Hourly

/**
 * author : Hyt
 * time : 2020/10/02
 * version : 1.0
 * 逐小时预报 RecyclerView 适配器
 */
class HourlyAdapter (layoutResId: Int,data: MutableList<Hourly>) :
    BaseQuickAdapter<Hourly, BaseViewHolder>(layoutResId,data) {

    override fun convert(holder: BaseViewHolder, item: Hourly) {
        val fxTime = data[holder.layoutPosition].fxTime
        val time = Utils.subString(fxTime,"T","+")

        holder.setText(R.id.item_Time,time)       //时间
     //   holder.setText(R.id.item_Time,data[holder.layoutPosition].fxDate)       //状态图标

        holder.setText(R.id.item_temp,"${data[holder.layoutPosition].temp}°")         //温度
        holder.setText(R.id.item_rainfallText,"${data[holder.layoutPosition].pop}%")  //降雨概率

    }

}