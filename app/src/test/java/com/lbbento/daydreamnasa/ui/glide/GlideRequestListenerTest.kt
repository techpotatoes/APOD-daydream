package com.lbbento.daydreamnasa.ui.glide

import com.flaviofaria.kenburnsview.KenBurnsView
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GlideRequestListenerTest {

    @Mock
    lateinit var imageView: KenBurnsView

    @Mock
    lateinit var apodImageLoaderListener: ApodImageLoaderListener

    @Test
    fun shouldResumeAnimationAndCallListenerOnResourceReady() {
        val glideRequestListener = GlideRequestListener(imageView = imageView, listenerApod = apodImageLoaderListener)

        glideRequestListener.onResourceReady(null, null, null, false, false)

        verify(imageView).resume()
        verify(apodImageLoaderListener).onApodImageReady()
    }

    @Test
    fun shouldPauseAnimationAndCallListenerOnException() {
        val glideRequestListener = GlideRequestListener(imageView = imageView, listenerApod = apodImageLoaderListener)

        glideRequestListener.onException(null, null, null, false)

        verify(imageView).pause()
        verify(apodImageLoaderListener).onApodImageException()
    }

}