package com.lbbento.daydreamnasa.main

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.data.repo.ApodRepository
import com.lbbento.daydreamnasa.di.AppSchedulers
import com.lbbento.daydreamnasa.presenter.BaseServicePresenter
import rx.Subscriber
import javax.inject.Inject

class MainDaydreamServiceViewPresenter @Inject constructor(val apodRepository: ApodRepository,
                                                           val appSchedulers: AppSchedulers,
                                                           val apodDataMapper: ApodDataMapper) : BaseServicePresenter<MainDaydreamServiceViewContract>() {

    fun onDreamingStarted() {
        apodRepository.getApod()
                .subscribeOn(appSchedulers.io())
                .observeOn(appSchedulers.ui())
                .subscribe(object : Subscriber<ApodDTO>() {
                    override fun onCompleted() {
                        mView.hideLoading()
                    }
                    override fun onError(e: Throwable) {
                        mView.showError()
                        mView.hideLoading()
                    }
                    override fun onNext(apodDTO: ApodDTO) {
                        val mainDaydreamServiceViewModel = apodDataMapper.apodDTOToMainDaydreamViewModel(apodDTO)
                        mView.loadContent(mainDaydreamServiceViewModel)
                    }
                    override fun onStart() {
                        mView.showLoading()
                    }
                })
    }


}