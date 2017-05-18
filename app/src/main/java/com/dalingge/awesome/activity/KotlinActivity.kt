package com.dalingge.awesome.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dalingge.awesome.R
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        textView7.text="hello kotlin"
    }
}
