package com.july.viewlibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.july.viewlibrary.R
import kotlinx.android.synthetic.main.activity_circle_progress.*

class CircleProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_progress)

        circleProgressButton0.setOnClickListener {
            circleProgress.setSchedule(60f)
            circleProgress.doAnimator(3000)
        }
    }
}