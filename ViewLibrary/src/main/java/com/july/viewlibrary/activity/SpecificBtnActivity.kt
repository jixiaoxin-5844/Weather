package com.july.viewlibrary.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hjq.toast.ToastUtils
import com.july.viewlibrary.R
import com.june.basicslibrary.utils.toast
import kotlinx.android.synthetic.main.activity_specific_btn.*

class SpecificBtnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_btn)
        initView()
    }

    private fun initView() {
     /*   specificBtn01.setRootClickListener(View.OnClickListener {
            toast("呜呜呜")
            //ToastUtils.show("呜呜呜")
        })*/

     /*   specificBtn02.setRootClickListener(View.OnClickListener {
            ToastUtils.show("视频")
        })*/



    }
}