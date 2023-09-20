package com.example.newtryofappwithworkingshifts.app

import android.app.Application
import com.example.newtryofappwithworkingshifts.di.domainModule
import com.example.newtryofappwithworkingshifts.di.presentationModule
import com.example.newtryofappwithworkingshifts.di.sharedPrefsModule
import com.example.newtryofappwithworkingshifts.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(dataModule, domainModule, presentationModule, sharedPrefsModule))
        }
    }
}