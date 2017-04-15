package com.lbbento.daydreamnasa.presenter

import com.lbbento.daydreamnasa.view.BaseServiceViewContract

interface BaseServicePresenterContract<in V> where V : BaseServiceViewContract {
    fun onAttachedToWindow(view: V)
}