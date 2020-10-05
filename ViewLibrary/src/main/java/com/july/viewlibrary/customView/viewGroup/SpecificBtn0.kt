package com.july.viewlibrary.customView.viewGroup

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.july.viewlibrary.R
import kotlinx.android.synthetic.main.specific_btn0.view.*

/**
 * author : Hyt
 * time : 2020/08/12
 * version : 1.0
 *
 */
class SpecificBtn0 : ConstraintLayout {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        LayoutInflater.from(context).inflate(R.layout.specific_btn0, this, true)

        val mTypeArray =
            context.obtainStyledAttributes(attrs, R.styleable.SpecificBtn0)

        val text = mTypeArray.getString(R.styleable.SpecificBtn0_btnText)
        val icon = mTypeArray.getResourceId(R.styleable.SpecificBtn0_icon, -1)
        val textColor =
            mTypeArray.getColor(R.styleable.SpecificBtn0_btnTextColor, Color.parseColor("#000000"))
        val textSize = mTypeArray.getDimension(R.styleable.SpecificBtn0_btnTextSize, 16f)

        mTypeArray.recycle()

        //使用属性
        if (text != null) {
            specificBtn0_tv0.text = text
            specificBtn0_tv0.setTextColor(textColor)
            specificBtn0_tv0.textSize = textSize
        }
        if (icon != -1) {
            specificBtn0_Img0.setImageResource(icon)
        } else {
            specificBtn0_Img0.visibility = View.GONE
        }
    }

    fun setTv1Text(text: String){
        specificBtn0_tv1.text = text
    }

}