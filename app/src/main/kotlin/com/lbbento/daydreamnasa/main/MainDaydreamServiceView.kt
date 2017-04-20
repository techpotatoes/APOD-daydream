package com.lbbento.daydreamnasa.main

import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.flaviofaria.kenburnsview.KenBurnsView
import com.lbbento.daydreamnasa.MainApplication
import com.lbbento.daydreamnasa.daydreamnasa.R
import com.lbbento.daydreamnasa.view.BaseServiceView
import java.lang.Exception
import javax.inject.Inject

class MainDaydreamServiceView : BaseServiceView(), MainDaydreamServiceViewContract {

    @Inject
    lateinit var presenter : MainDaydreamServiceViewPresenter

    @BindView(R.id.main_dreamserviceview_wallImg)
    lateinit var wallImage : KenBurnsView

    @BindView(R.id.main_dreamserviceview_title)
    lateinit var textTitle : TextView

    @BindView(R.id.main_dreamserviceview_description)
    lateinit var textDescription : TextView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onAttachedToWindow(this)
    }

    override fun setScreenContent() {
        isFullscreen = true
        isInteractive = true
        isScreenBright = false

        val view = View.inflate(applicationContext, R.layout.main_dreamservice_view, null)
        ButterKnife.bind(this, view)
        setContentView(view)
    }

    override fun setupInjection() {
        (application as MainApplication).component.inject(this)
    }

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        presenter.onDreamingStarted()
    }

    override fun showLoading() {
        //TODO - show small spinner
    }
    override fun hideLoading() {
        //TODO - hide spinner
    }
    override fun showError() {
        Toast.makeText(this, R.string.loading_error_message, Toast.LENGTH_SHORT).show()
    }
    override fun loadContent(mainDaydreamServiceViewModel: MainDaydreamServiceViewModel) {
        Log.d("LUCAS", mainDaydreamServiceViewModel.imageUrl)

        Glide
                .with(this)
                .load(mainDaydreamServiceViewModel.imageUrl)
                .placeholder(R.drawable.earth)
                .fallback(R.drawable.earth)
                .animate(R.anim.abc_fade_in)
                .listener(GlideRequestListener(wallImage))
                .into(wallImage)

        textTitle.text = mainDaydreamServiceViewModel.title
        textDescription.text = mainDaydreamServiceViewModel.description
    }

    private class GlideRequestListener(val wallImage: KenBurnsView) : RequestListener<String, GlideDrawable> {

        override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
            wallImage.resume()
            return false
        }

        override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
            wallImage.pause()
            return false
        }

    }
}
