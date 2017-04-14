package com.lbbento.daydreamnasa.presenter

import com.lbbento.daydreamnasa.view.BaseViewContract


abstract class BasePresenter<V : BaseViewContract> : BasePresenterContract<V> {

    lateinit var mView : V

    override fun onAttachedToWindow(view: V) {
            this.mView = view
            mView.setScreenContent()
    }
}