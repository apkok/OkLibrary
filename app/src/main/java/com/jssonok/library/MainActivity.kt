package com.jssonok.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jssonok.library.demo.OkLogDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.tv_ok_log -> {
                startActivity(Intent(this, OkLogDemoActivity::class.java))
            }
        }
    }
}