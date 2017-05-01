package com.lbbento.daydreamnasa.ui.presenter

import com.lbbento.daydreamnasa.ui.view.BaseViewContract

interface BasePresenterContract<in V> where V : BaseViewContract {
    fun onAttachedToWindow(view: V)
}