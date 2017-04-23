package com.lbbento.daydreamnasa.main

import android.net.Uri
import com.lbbento.daydreamnasa.view.BaseServiceViewContract

interface MainDaydreamServiceViewContract : BaseServiceViewContract {
    fun showLoadingError()
    fun showError()
    fun loadContent(mainDaydreamServiceViewModel: MainDaydreamServiceViewModel)
    fun openYoutubeVideo(videoUrl: String)
    fun openExplictIntentVideo(explictIntentUri: Uri?)
    fun openImplictIntentVideo(imageUri: Uri?)
}