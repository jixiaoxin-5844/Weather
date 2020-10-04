package com.july.viewlibrary.customView.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.july.viewlibrary.R

/**
 * author : Hyt
 * time : 2020/07/18
 * version : 1.0
 * 背景圆
 */
class CircleBackground(context: Context, attrs: AttributeSet) : View(context,attrs,0) {


    // 此函数不会被自动调用，一般会在两个参数的函数中调用，比如给 View 设置 style 属性时
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs) {
        val mTypeArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircleBackground)

        lineBackground = mTypeArray.getColor(R.styleable.CircleBackground_lineBackground, Color.RED)
        lineDegree = mTypeArray.getFloat(R.styleable.CircleBackground_lineDegree, 12f)

        mTypeArray.recycle()
    }


    // 参数表示开启抗锯齿
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var lineBackground = Color.RED
    private var lineDegree = 12f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mMeasureWidth = measuredWidth
        var mMeasureHeight = measuredHeight
        if (mMeasureWidth > measuredHeight) {
            mMeasureWidth = measuredHeight
        } else {
            mMeasureHeight = measuredWidth
        }
        setMeasuredDimension(mMeasureWidth, mMeasureHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //获取View自身 的宽高
        val mHeight = height.toFloat()
        val mWidth = width.toFloat()
        paint.run {
            color = lineBackground
            style = Paint.Style.STROKE //画线模式
            strokeWidth = lineDegree
        }
        //圆半径 =  View宽/2 - 线条粗细/2
        canvas?.drawCircle(
            mHeight/2,mWidth/2,
            mHeight / 2 - lineDegree/2, paint
        )
    }

}