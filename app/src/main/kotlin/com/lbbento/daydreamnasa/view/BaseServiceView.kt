package com.lbbento.daydreamnasa.view

import android.service.dreams.DreamService

abstract class BaseServiceView : DreamService(), BaseServiceViewContract {

    abstract fun setupInjection()

    override fun onCreate() {
        super.onCreate()
        setupInjection()
    }
}

