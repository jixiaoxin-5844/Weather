package com.june.basicslibrary.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.june.basicslibrary.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}