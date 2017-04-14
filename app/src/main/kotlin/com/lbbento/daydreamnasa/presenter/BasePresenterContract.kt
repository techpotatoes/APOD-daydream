package com.lbbento.daydreamnasa.presenter

import com.lbbento.daydreamnasa.view.BaseViewContract

interface BasePresenterContract<in V> where V : BaseViewContract {
    fun onAttach(view: Any)
}