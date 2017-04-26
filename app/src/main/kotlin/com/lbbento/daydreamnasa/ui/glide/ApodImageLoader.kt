package com.lbbento.daydreamnasa.ui.glide

import com.bumptech.glide.Glide
import com.flaviofaria.kenburnsview.KenBurnsView
import com.lbbento.daydreamnasa.daydreamnasa.R

class ApodImageLoader(val listenerApod: ApodImageLoaderListener) {

    fun loadApodImage(imageView: KenBurnsView, imageUrl: String) {
        Glide
                .with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.earth)
                .fallback(R.drawable.earth)
                .animate(R.anim.abc_fade_in)
                .listener(GlideRequestListener(imageView, listenerApod))
                .into(imageView)
    }
}