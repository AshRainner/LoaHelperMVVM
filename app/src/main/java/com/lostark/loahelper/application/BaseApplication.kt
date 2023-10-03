package com.lostark.loahelper.application

import android.app.Application
import com.lostark.loahelper.module.diModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(diModule) // diModule을 등록
        }
    }
}