package com.jssonok.library

import android.app.Application
import com.google.gson.Gson
import com.jssonok.library.log.OkConsolePrinter
import com.jssonok.library.log.OkLogConfig
import com.jssonok.library.log.OkLogManager

class MApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        OkLogManager.init(object : OkLogConfig(){
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }
            override fun getGlobalTag(): String {
                return "MApplication"
            }

            override fun enable(): Boolean {
                return true
            }
        }, OkConsolePrinter())
    }
}