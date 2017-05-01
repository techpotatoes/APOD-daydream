package com.lbbento.daydreamnasa.main.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
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
import com.lbbento.daydreamnasa.ui.view.BaseActivityView
import javax.inject.Inject

class MainDaydreamActivity: BaseActivityView(), MainDaydreamViewContract {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apodImageLoader = ApodImageLoader(this)
        presenter.onAttachedToWindow(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onDreamingStarted()
    }

    override fun setupScreen() {
        setContentView(R.layout.main_dreamservice_view)
        ButterKnife.bind(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        presenter.onTouchEvent()
        return false
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        presenter.onKeyEvent(event)
        return false
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
        startActivity(Intent(Intent.ACTION_VIEW, explictIntentUri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    @Throws(Exception::class)
    override fun openImplictIntentVideo(imageUri: Uri?) {
        startActivity(Intent(Intent.ACTION_VIEW, imageUri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    override fun onApodImageReady() {
        presenter.onApodImageReady()
    }

    override fun onApodImageException() {
        presenter.onApodImageException()
    }

}
