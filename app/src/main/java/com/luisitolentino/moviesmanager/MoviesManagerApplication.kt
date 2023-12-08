package com.luisitolentino.moviesmanager

import android.app.Application
import com.luisitolentino.moviesmanager.framework.di.appModules
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MoviesManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MoviesManagerApplication)
            modules(appModules)
        }
    }
}