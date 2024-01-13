package com.honorida

import android.app.Application
import com.honorida.modules.AppModuleImpl
import com.honorida.modules.IAppModule

class HonoridaApp : Application() {
    companion object {
        lateinit var appModule: IAppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}