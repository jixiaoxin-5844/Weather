package com.july.viewlibrary.customView.viewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT


/**
 * author : Hyt
 * time : 2020/07/25
 * version : 1.0
 * 历史搜索记录 ->参考qq浏览器搜索记录
 */
class TestViewGroup : ViewGroup {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context, attrs, defStyleAttr, defStyleRes
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs, defStyleAttr, 0
    ) {
    }

    //计算子View的位置和尺寸，以及自己的尺寸
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if(childCount == 0){
            setMeasuredDimension(0,0)
        }

        //1 调用子View的measure(),让子View自我测量
        for(i in 0..childCount){
            val childView = getChildAt(i)

            val lp = childView.layoutParams

            val mode = MeasureSpec.getMode(widthMeasureSpec) //获取ViewGroup (自己)的width的 mode
            val size = MeasureSpec.getSize(widthMeasureSpec) //width的 size
            lp.width
            lp.height //对应width和height属性

            var childWidthSpec =   MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED)

            when(lp.width){ //判断子View的 width属性是什么，并作出相应处理
                 MATCH_PARENT ->{
                     if(mode == EXACTLY || mode == AT_MOST){           //自己的可用宽度
                       //  childWidthSpec = MeasureSpec.makeMeasureSpec(size - userWidth, EXACTLY)
                     }else{
                         childWidthSpec =
                             MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED)
                     }
                 }
                WRAP_CONTENT ->{
                    if(mode == EXACTLY || mode == AT_MOST){
                       // childWidthSpec = MeasureSpec.makeMeasureSpec(size - userWidth, AT_MOST)
                    }else{
                        childWidthSpec =
                            MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED)
                    }
                }
                else -> {
                    childWidthSpec = MeasureSpec.makeMeasureSpec(lp.width, EXACTLY)
                }
            }

           // measureChild()

        }


        //2 根据子View给出的尺寸，得出子View的位置，并保存他们的位置和尺寸
        //3 根据子View的位置和尺寸计算自己的尺寸，并保存
        //还需要对padding margin 进行处理


    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        //摆放子View
        for (i in 0..childCount){
            val childView = getChildAt(i)
            childView.layout(left,top,right,bottom)
        }


    }


}