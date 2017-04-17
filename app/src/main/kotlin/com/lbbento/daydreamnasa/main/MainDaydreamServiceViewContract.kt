package com.lbbento.daydreamnasa.main

import com.lbbento.daydreamnasa.view.BaseServiceViewContract

interface MainDaydreamServiceViewContract : BaseServiceViewContract {
    fun showLoading()
    fun hideLoading()
    fun showError()
    fun loadContent(mainDaydreamServiceViewModel: MainDaydreamServiceViewModel)
}