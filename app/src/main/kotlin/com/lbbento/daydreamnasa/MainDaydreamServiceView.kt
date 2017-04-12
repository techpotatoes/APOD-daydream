package com.lbbento.daydreamnasa

import android.service.dreams.DreamService
import com.lbbento.daydreamnasa.daydreamnasa.R

class MainDaydreamServiceView : DreamService(), MainDayDreamServiceViewContract {

    val mainDaydreamServiceViewPresenter = MainDaydreamServiceViewPresenter(this)

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        mainDaydreamServiceViewPresenter.onDreamingStarted()
    }

    override fun setScreenContent() {
        isFullscreen = true
        isInteractive = true
        isScreenBright = false

        println("Im here")
        setContentView(R.layout.activity_main)
    }

}
