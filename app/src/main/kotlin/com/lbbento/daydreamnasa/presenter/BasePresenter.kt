package com.lbbento.daydreamnasa.presenter

import com.lbbento.daydreamnasa.view.BaseViewContract

abstract class BasePresenter<V : BaseViewContract> : BasePresenterContract<V> {

    lateinit var mView : V

    override fun onAttach(view: Any) {
        mView = view as V
    }
}