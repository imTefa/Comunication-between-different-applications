package com.example.middleman.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.middleman.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        finish()
    }
}
