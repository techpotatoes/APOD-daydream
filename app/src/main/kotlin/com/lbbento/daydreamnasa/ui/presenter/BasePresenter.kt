package com.lbbento.daydreamnasa.ui.presenter

import com.lbbento.daydreamnasa.ui.view.BaseViewContract


abstract class BasePresenter<V : BaseViewContract> : BasePresenterContract<V> {

    lateinit var mView : V

    override fun onAttachedToWindow(view: V) {
            this.mView = view
            mView.setupScreen()
    }
}