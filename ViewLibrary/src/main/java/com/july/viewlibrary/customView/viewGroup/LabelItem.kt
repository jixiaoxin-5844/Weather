package com.july.viewlibrary.customView.viewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.july.viewlibrary.R
import com.july.viewlibrary.customView.interfaceA.OnLabelItemClickListener
import kotlinx.android.synthetic.main.search_item.view.*

/**
 * author : Hyt
 * time : 2020/07/26
 * version : 1.0
 * 搜索栏的item ->组合控件
 */
class LabelItem : ConstraintLayout {

    private lateinit var onItemClickListener: OnLabelItemClickListener

    fun setOnItemClickListener(clickListener: OnLabelItemClickListener){
        this.onItemClickListener = clickListener
    }

    constructor(context: Context) : super(context){initView(context)}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){initView(context)}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    constructor(context: Context,text: String):super(context){
        initView(context)
        searchText.text = text
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.search_item, this, true)

        searchItemRoot.setOnClickListener {
            try {
                onItemClickListener.onItemClickListener(this@LabelItem, searchText.text.toString())
            } catch (e: Exception) {
            }
        }

    }

    fun setRootBackgroundColor(color:Int){
        searchItemCard.setCardBackgroundColor(color)

    }


}