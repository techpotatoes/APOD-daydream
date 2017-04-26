package com.lbbento.daydreamnasa.ui.glide

import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.flaviofaria.kenburnsview.KenBurnsView

class GlideRequestListener(val imageView: KenBurnsView, val listenerApod: ApodImageLoaderListener) : RequestListener<String, GlideDrawable> {

    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
        imageView.resume()
        listenerApod.onApodImageReady()
        return false
    }

    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
        imageView.pause()
        listenerApod.onApodImageException()
        return false
    }
}