package com.snapd

import android.app.Application
import com.snapd.di.dataModule
import com.snapd.di.domainModule
import com.snapd.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PlinApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@PlinApp)
            modules(listOf(domainModule, dataModule, presentationModule))
        }
    }
}