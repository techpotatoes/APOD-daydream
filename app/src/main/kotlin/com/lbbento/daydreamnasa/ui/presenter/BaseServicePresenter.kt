package com.lbbento.daydreamnasa.ui.presenter

import com.lbbento.daydreamnasa.ui.view.BaseServiceViewContract


abstract class BaseServicePresenter<V : BaseServiceViewContract> : BaseServicePresenterContract<V> {

    lateinit var mView : V

    override fun onAttachedToWindow(view: V) {
            this.mView = view
            mView.setupScreen()
    }
}