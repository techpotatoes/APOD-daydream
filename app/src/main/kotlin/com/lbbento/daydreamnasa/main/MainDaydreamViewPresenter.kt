package com.lbbento.daydreamnasa.main

import android.content.ActivityNotFoundException
import android.view.KeyEvent
import com.lbbento.daydreamnasa.AppUtil
import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.data.repo.ApodRepository
import com.lbbento.daydreamnasa.di.AppSchedulers
import com.lbbento.daydreamnasa.main.model.MainDaydreamDataMapper
import com.lbbento.daydreamnasa.main.model.MainDaydreamViewModel
import com.lbbento.daydreamnasa.ui.presenter.BasePresenter
import rx.Subscriber
import javax.inject.Inject

class MainDaydreamViewPresenter @Inject constructor(val apodRepository: ApodRepository,
                                                    val appSchedulers: AppSchedulers,
                                                    val mainDaydreamDataMapper: MainDaydreamDataMapper) : BasePresenter<MainDaydreamViewContract>() {

    var mainDaydreamViewModelState: MainDaydreamViewModel = MainDaydreamViewModel()

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
                        val mainDaydreamServiceViewModel = mainDaydreamDataMapper.apodDTOToMainDaydreamViewModel(apodDTO)
                        mainDaydreamViewModelState = mainDaydreamServiceViewModel
                        mView.loadImage(imageUrl = mainDaydreamServiceViewModel.imageUrl)
                    }
                    override fun onStart() {
                    }
                })
    }

    fun onKeyEvent(event: KeyEvent?) {
        if (event!!.action == KeyEvent.ACTION_UP) {
            if (event.keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                if (mainDaydreamViewModelState.mediaType == "video") mView.openYoutubeVideo(mainDaydreamViewModelState.originalUrl)
            } else if (event.keyCode == KeyEvent.KEYCODE_BACK){
                mView.finish()
            }
        }
    }

    fun onTouchEvent() {
        mView.finish()
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
        setViewData(mainDaydreamViewModelState)
    }

    fun onApodImageException() {
        mView.showError()
    }

    private fun setViewData(mainDaydreamViewModel: MainDaydreamViewModel) {
        mView.loadTitle(title = mainDaydreamViewModel.title)
        mView.loadDescription(description = mainDaydreamViewModel.description)
        mView.showVideoLink(showHint = mainDaydreamViewModel.mediaType == "video")
    }
}