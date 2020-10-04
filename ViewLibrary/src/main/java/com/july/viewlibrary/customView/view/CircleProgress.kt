package com.july.viewlibrary.customView.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.july.viewlibrary.R

/**
 * author : Hyt
 * time : 2020/07/23
 * version : 1.0
 * 进度圆
 */
class CircleProgress: View {

    constructor(context: Context,attrs: AttributeSet):this(context,attrs,0)
    constructor(context: Context,attrs: AttributeSet,defStyleAttr: Int,defStyleRes: Int):super(context, attrs, defStyleAttr, defStyleRes)



    private var backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)         // 外圈圆 参数表示开启抗锯齿
    private var scheduleTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)           // 中心文字

    private var lineBackground = Color.BLUE
    private var lineScheduleBackground = Color.RED
    private var schedule = 0f
    private var lineDegree = 12f
    private var startAngle = -90f
    private var scheduleText = -1f
    private var scheduleTextColor = Color.RED
    private var scheduleTextSize = 20f

    private val rectF = RectF()

    constructor(context: Context, attrs: AttributeSet,defStyleAttr:Int) : super(context,attrs, defStyleAttr, 0) {
        val mTypeArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircleProgress)

        lineBackground = mTypeArray.getColor(R.styleable.CircleProgress_lineBackground1, Color.BLUE)
        lineScheduleBackground = mTypeArray.getColor(R.styleable.CircleProgress_lineScheduleBackground,Color.RED)
        schedule = mTypeArray.getFloat(R.styleable.CircleProgress_schedule,0f)
        lineDegree = mTypeArray.getFloat(R.styleable.CircleProgress_lineDegree1,12f)
        startAngle = mTypeArray.getFloat(R.styleable.CircleProgress_startAngle,-90f)
        scheduleText = mTypeArray.getFloat(R.styleable.CircleProgress_scheduleText,-1f)
        scheduleTextColor = mTypeArray.getColor(R.styleable.CircleProgress_scheduleTextColor,Color.RED)
        scheduleTextSize = mTypeArray.getDimension(R.styleable.CircleProgress_scheduleTextSize,20f)

        mTypeArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mMeasureWidth = measuredWidth
        var mMeasureHeight = measuredHeight
        if(mMeasureWidth > measuredHeight){
            mMeasureWidth = measuredHeight
        }else{
            mMeasureHeight = measuredWidth
        }
        setMeasuredDimension(mMeasureWidth,mMeasureHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val mHeight = height.toFloat()
        val mWidth = width.toFloat()

        backgroundPaint.run {
            color = lineBackground
            style = Paint.Style.STROKE //画线模式
            strokeWidth = lineDegree
        }

        canvas?.drawCircle(
            mHeight/2,mWidth/2,
            mHeight / 2 - lineDegree/2, backgroundPaint
        )

        backgroundPaint.color = lineScheduleBackground

        /* RectF 参数：
        left 长方形左侧的x坐标    top 长方形顶的Y坐标
        right 长方形右侧的X坐标   bottom 长方形底的Y坐标*/
        rectF.set(lineDegree/2,lineDegree/2,
            mHeight-lineDegree/2,mWidth-lineDegree/2)

        //任务进度 进度圆
        canvas?.drawArc(rectF,startAngle, getSchedule() / 100 * 360,
            false,backgroundPaint)

        if(scheduleText != -1f){ //设置了值，才绘制
            scheduleTextPaint.run {
                color = scheduleTextColor
                textSize = scheduleTextSize
            }
            canvas?.drawText("$scheduleText",mHeight/3,mWidth*0.6f,scheduleTextPaint)
        }

    }

    private fun getSchedule():Float{
        return schedule
    }


    /**
     * 动画
     * @param duration 动画时长
    * */
    fun doAnimator(duration: Long) {
        val animator = ObjectAnimator.ofFloat(this, "schedule",0f,getSchedule())
        animator.duration = 3000
        animator.start()
    }

    //文字颜色
    fun setScheduleTextColor(color:Int){
        this.scheduleTextColor = color
    }

    //文字大小
    fun setScheduleTestSize(mSchedule: Float){
        this.scheduleTextSize = mSchedule
    }

    //进度
    fun setSchedule(mSchedule:Float){
        this.schedule = mSchedule
        invalidate()
    }

    //中心文字百分比 (应该和进度一样)
    fun setScheduleText(mSchedule: Float){
        this.scheduleText = mSchedule
    }

    //线条背景颜色
    fun setLineColor(color:Int){
        this.lineBackground = color
    }
    //线条进度颜色
    fun setScheduleColor(color:Int){
        this.lineScheduleBackground = color
    }

    //进度开始的地方 -> 默认-90f 正12点钟位置
    fun setStartAngle(mStartAngle:Float){
        this.startAngle = mStartAngle
    }
}