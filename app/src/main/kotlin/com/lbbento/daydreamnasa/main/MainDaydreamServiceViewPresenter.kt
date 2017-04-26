package com.lbbento.daydreamnasa.main

import android.content.ActivityNotFoundException
import android.view.KeyEvent
import com.lbbento.daydreamnasa.AppUtil
import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.data.repo.ApodRepository
import com.lbbento.daydreamnasa.di.AppSchedulers
import com.lbbento.daydreamnasa.ui.presenter.BaseServicePresenter
import rx.Subscriber
import javax.inject.Inject

class MainDaydreamServiceViewPresenter @Inject constructor(val apodRepository: ApodRepository,
                                                           val appSchedulers: AppSchedulers,
                                                           val apodDataMapper: ApodDataMapper) : BaseServicePresenter<MainDaydreamServiceViewContract>() {

    var mainDaydreamServiceViewModelState: MainDaydreamServiceViewModel = MainDaydreamServiceViewModel()

    fun onDreamingStarted() {
        apodRepository.getApod()
                .subscribeOn(appSchedulers.io())
                .observeOn(appSchedulers.ui())
                .subscribe(object : Subscriber<ApodDTO>() {
                    override fun onCompleted() {
                    }
                    override fun onError(e: Throwable) {
                        mView.showLoadingError()
                    }
                    override fun onNext(apodDTO: ApodDTO) {
                        val mainDaydreamServiceViewModel = apodDataMapper.apodDTOToMainDaydreamViewModel(apodDTO)
                        mainDaydreamServiceViewModelState = mainDaydreamServiceViewModel
                        mView.loadImage(imageUrl = mainDaydreamServiceViewModel.imageUrl)
                    }
                    override fun onStart() {
                    }
                })
    }

    fun onDispatchKeyEvent(event: KeyEvent?) {
        if (event!!.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
            mView.openYoutubeVideo(mainDaydreamServiceViewModelState.originalUrl)
    }

    fun onOpenYoutubeVideo(videoUrl: String) {
        try {
            val imageUri = mView.parseUri(videoUrl)
            val youtubeId = AppUtil.extractYoutubeId(videoUrl)
            val explictIntentUri = mView.parseUri("vnd.youtube:" + youtubeId)

            try {
                mView.openExplictIntentVideo(explictIntentUri)
            } catch (ex: ActivityNotFoundException) {
                mView.openImplictIntentVideo(imageUri)
            }
        } catch (e: Exception) {
            mView.showError()
        }
    }

    fun onApodImageReady() {
        setViewData(mainDaydreamServiceViewModelState)
    }

    fun onApodImageException() {
        mView.showError()
    }

    private fun setViewData(mainDaydreamServiceViewModel: MainDaydreamServiceViewModel) {
        mView.loadTitle(title = mainDaydreamServiceViewModel.title)
        mView.loadDescription(description = mainDaydreamServiceViewModel.description)
    }
}