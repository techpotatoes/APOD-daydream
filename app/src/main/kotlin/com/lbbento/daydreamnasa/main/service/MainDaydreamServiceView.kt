package com.lbbento.daydreamnasa.main.service

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.flaviofaria.kenburnsview.KenBurnsView
import com.lbbento.daydreamnasa.MainApplication
import com.lbbento.daydreamnasa.daydreamnasa.R
import com.lbbento.daydreamnasa.main.MainDaydreamViewContract
import com.lbbento.daydreamnasa.main.MainDaydreamViewPresenter
import com.lbbento.daydreamnasa.ui.glide.ApodImageLoader
import com.lbbento.daydreamnasa.ui.view.BaseServiceView
import javax.inject.Inject


class MainDaydreamServiceView : BaseServiceView(), MainDaydreamViewContract {

    @Inject
    lateinit var presenter : MainDaydreamViewPresenter

    lateinit var apodImageLoader: ApodImageLoader

    @BindView(R.id.main_dreamserviceview_wallImg)
    lateinit var wallImage : KenBurnsView

    @BindView(R.id.main_dreamserviceview_title)
    lateinit var textTitle : TextView

    @BindView(R.id.main_dreamserviceview_description)
    lateinit var textDescription : TextView

    @BindView(R.id.main_dreamserviceview_videolinkhint)
    lateinit var textVideoLink : TextView

    @BindView(R.id.main_dreamserviceview_container)
    lateinit var container : ViewGroup

    override fun setupInjection() {
        (application as MainApplication).component.inject(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onAttachedToWindow(this)
    }

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        apodImageLoader = ApodImageLoader(this)
        presenter.onDreamingStarted()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        presenter.onKeyEvent(event)
        return super.dispatchKeyEvent(event)
    }

    override fun setupScreen() {
        isFullscreen = true
        isInteractive = true
        isScreenBright = false

        val view = View.inflate(applicationContext, R.layout.main_dreamservice_view, null)
        ButterKnife.bind(this, view)
        setContentView(view)
    }
    
    override fun loadImage(imageUrl: String) {
        apodImageLoader.loadApodImage(wallImage, imageUrl)
    }

    override fun loadTitle(title: String) {
        textTitle.text = title
    }

    override fun loadDescription(description: String) {
        textDescription.text = description
    }

    override fun showVideoLink(showHint: Boolean) {
        textVideoLink.visibility = if (showHint) View.VISIBLE else View.GONE
    }

    override fun showLoadingError() {
        Toast.makeText(this, R.string.main_dreamservice_view_loading_error_message, Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(this, R.string.main_dreamservice_view_error, Toast.LENGTH_SHORT).show()
    }

    override fun openYoutubeVideo(videoUrl: String) {
        presenter.onOpenYoutubeVideo(videoUrl)
    }

    @Throws(ActivityNotFoundException::class)
    override fun openExplictIntentVideo(explictIntentUri: Uri?) {
        startActivity(Intent(Intent.ACTION_VIEW, explictIntentUri).addFlags(FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    @Throws(Exception::class)
    override fun openImplictIntentVideo(imageUri: Uri?) {
        startActivity(Intent(Intent.ACTION_VIEW, imageUri).addFlags(FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    override fun onApodImageReady() {
        presenter.onApodImageReady()
    }

    override fun onApodImageException() {
        presenter.onApodImageException()
    }
}
