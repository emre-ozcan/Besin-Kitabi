package com.emreozcan.besinkitabi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.emreozcan.besinkitabi.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_crockery)
        supportActionBar?.setTitle("  Besin Kitabı")
    }
}