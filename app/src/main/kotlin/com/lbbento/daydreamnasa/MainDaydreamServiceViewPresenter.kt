package com.lbbento.daydreamnasa

class MainDaydreamServiceViewPresenter(var view: MainDayDreamServiceViewContract) {

    fun onDreamingStarted() {
        view.setScreenContent()
    }
    
}