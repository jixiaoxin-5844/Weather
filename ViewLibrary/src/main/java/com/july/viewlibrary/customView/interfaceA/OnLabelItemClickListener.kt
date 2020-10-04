package com.july.viewlibrary.customView.interfaceA

import android.view.View

/**
 * author : Hyt
 * time : 2020/07/31
 * version : 1.0
 *
 */
interface OnLabelItemClickListener {
    fun onItemClickListener(view: View, text: String)
}

interface OnLabelViewClickListener {
    fun onItemClickListener(view: View, position:Int, text: String)
}



