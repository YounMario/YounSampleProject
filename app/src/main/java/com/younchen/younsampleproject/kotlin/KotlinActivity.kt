package com.younchen.younsampleproject.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.younchen.younsampleproject.R

class KotlinActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        initUI()
    }

    private fun initUI() {
        val foo = Foo("rocky", 23)
        val textView = findViewById(R.id.txt_message) as TextView
        textView.text = foo.name + ":" + foo.age
    }


}
