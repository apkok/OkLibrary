package com.jssonok.library.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jssonok.library.R
import com.jssonok.library.log.*

class OkLogDemoActivity : AppCompatActivity() {

    var viewPrinter: OkViewPrinter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_log_demo)
        viewPrinter = OkViewPrinter(this)
        findViewById<View>(R.id.btn_log).setOnClickListener{
            printLog()
        }

        viewPrinter!!.viewProvider.showFloatingView()
    }

    private fun printLog() {
        // 添加日志视图打印器
        OkLogManager.getInstance().addPrinter(viewPrinter)
        // 自定义Log配置
        OkLog.log(object : OkLogConfig(){
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, "------", OkLogType.E, "5566")
        OkLog.a("7788")
    }
}