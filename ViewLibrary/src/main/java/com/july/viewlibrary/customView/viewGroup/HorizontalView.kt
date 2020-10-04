package com.july.viewlibrary.customView.viewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.ViewGroup
import com.july.viewlibrary.R

/**
 * author : Hyt
 * time : 2020/07/26
 * version : 1.0
 *
 */
class HorizontalView : ViewGroup {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr) {
        /* val mTypeArray =
             context.obtainStyledAttributes(attrs, R.styleable.SearchView)

         mTypeArray.recycle()*/
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        measureChildren(widthMeasureSpec,heightMeasureSpec)
        if(childCount == 0){
            setMeasuredDimension(0,0)
        }else if(widthMode == AT_MOST && heightMode == AT_MOST){
            val child0 = getChildAt(0)
            val childWidth = child0.measuredWidth
            val childHeight = child0.measuredHeight
            setMeasuredDimension(childWidth * childCount, childHeight)
        }else if(widthMode == AT_MOST){
            val childWidth = getChildAt(0).measuredWidth
            setMeasuredDimension(childWidth * childCount, heightSize)
        }else if(heightMode == AT_MOST){
            val childHeight = getChildAt(0).measuredHeight
            setMeasuredDimension(widthSize,childHeight)
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var left = 0
        for(i in 0 until childCount){
            val child = getChildAt(i)
            if(child.visibility != View.GONE){
                val measuredWidth = child.measuredWidth

                child.layout(left,0,left+measuredWidth, child.measuredHeight)
                left += width
            }
        }

    }
}