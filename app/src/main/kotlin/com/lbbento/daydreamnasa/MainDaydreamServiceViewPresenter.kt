package com.lbbento.daydreamnasa

import com.lbbento.daydreamnasa.presenter.BasePresenter
import com.lbbento.daydreamnasa.view.ImageLoader

class MainDaydreamServiceViewPresenter : BasePresenter<MainDayDreamServiceViewContract>() {

    lateinit var imageLoader : ImageLoader

    fun onDreamingStarted() {
        mView.setScreenContent()
    }

}