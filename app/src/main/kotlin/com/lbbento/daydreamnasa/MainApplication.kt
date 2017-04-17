package com.lbbento.daydreamnasa

import android.app.Application
import com.facebook.stetho.Stetho
import com.lbbento.daydreamnasa.di.AppComponent
import com.lbbento.daydreamnasa.di.AppModule
import com.lbbento.daydreamnasa.di.DaggerAppComponent

class MainApplication : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)
    }

}