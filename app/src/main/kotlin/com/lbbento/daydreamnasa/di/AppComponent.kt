package com.lbbento.daydreamnasa.di

import com.lbbento.daydreamnasa.MainApplication
import com.lbbento.daydreamnasa.main.activity.MainDaydreamActivity
import com.lbbento.daydreamnasa.main.service.MainDaydreamServiceView
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(mainApplication : MainApplication)
    fun inject(mainDayDreamServiceView : MainDaydreamServiceView)
    fun inject(mainDaydreamActivity: MainDaydreamActivity)
}