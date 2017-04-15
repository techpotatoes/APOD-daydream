package com.lbbento.daydreamnasa.presenter

import com.lbbento.daydreamnasa.view.BaseServiceViewContract


abstract class BaseServicePresenter<V : BaseServiceViewContract> : BaseServicePresenterContract<V> {

    lateinit var mView : V

    override fun onAttachedToWindow(view: V) {
            this.mView = view
            mView.setScreenContent()
    }
}