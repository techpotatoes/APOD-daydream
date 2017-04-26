package com.lbbento.daydreamnasa.ui.presenter

import com.lbbento.daydreamnasa.ui.view.BaseServiceViewContract

interface BaseServicePresenterContract<in V> where V : BaseServiceViewContract {
    fun onAttachedToWindow(view: V)
}