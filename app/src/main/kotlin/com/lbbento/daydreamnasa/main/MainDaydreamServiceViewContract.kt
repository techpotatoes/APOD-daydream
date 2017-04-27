package com.lbbento.daydreamnasa.main

import android.net.Uri
import com.lbbento.daydreamnasa.ui.glide.ApodImageLoaderListener
import com.lbbento.daydreamnasa.ui.view.BaseServiceViewContract

interface MainDaydreamServiceViewContract : BaseServiceViewContract, ApodImageLoaderListener {
    fun showLoadingError()
    fun showError()
    fun loadImage(imageUrl: String)
    fun loadTitle(title: String)
    fun loadDescription(description: String)
    fun openYoutubeVideo(videoUrl: String)
    fun openExplictIntentVideo(explictIntentUri: Uri?)
    fun openImplictIntentVideo(imageUri: Uri?)
    fun showVideoLink(showHint: Boolean)
}