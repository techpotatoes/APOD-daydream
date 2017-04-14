package com.lbbento.daydreamnasa.view

import android.service.dreams.DreamService
import com.lbbento.daydreamnasa.presenter.BasePresenterContract

abstract class BaseServiceView<out P : BasePresenterContract<*>>(factory: () -> P) : DreamService(), BaseViewContract {

    val presenter = factory() //TODO - Revisit this implementation

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        presenter.onAttach(this)
    }
}