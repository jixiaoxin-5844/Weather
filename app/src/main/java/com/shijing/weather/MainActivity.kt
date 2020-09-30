package com.shijing.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.june.basicslibrary.utils.UtilToast

class MainActivity : AppCompatActivity() {

    //private val viewmodel by viewModels<>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UtilToast.show("")
        //test
    }
}