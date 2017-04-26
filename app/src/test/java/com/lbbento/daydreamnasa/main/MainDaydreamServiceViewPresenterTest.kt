package com.lbbento.daydreamnasa.main

import android.accounts.NetworkErrorException
import android.content.ActivityNotFoundException
import android.net.Uri
import android.view.KeyEvent
import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.data.repo.ApodRepository
import com.lbbento.daydreamnasa.di.AppSchedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import rx.internal.schedulers.ImmediateScheduler
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainDaydreamServiceViewPresenterTest {

    @Mock
    lateinit var apodDataMapper : ApodDataMapper

    @Mock
    lateinit var apodRepository : ApodRepository

    @Mock
    lateinit var appSchedulers : AppSchedulers

    @Mock
    lateinit var mainDaydreamServiceView: MainDaydreamServiceViewContract

    @InjectMocks
    lateinit var mainDaydreamServiceViewPresenter: MainDaydreamServiceViewPresenter

    private val APOD_DTO = ApodDTO(date = Date(), explanation = "My description", hdurl = "url", copyright = "", media_type = "", service_version = "", title = "title", url = "")

    @Before
    fun setup() {
        `when`(appSchedulers.io()).thenReturn(ImmediateScheduler.INSTANCE)
        `when`(appSchedulers.ui()).thenReturn(ImmediateScheduler.INSTANCE)

        mainDaydreamServiceViewPresenter.onAttachedToWindow(mainDaydreamServiceView)
    }

    @Test
    fun shouldRetrieveDataOnDreamingStarted() {
        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))

        mainDaydreamServiceViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
    }
    @Test
    fun shouldShowErrorWhenRetrievingDataOnDreamingStartedFails() {
        `when`(apodRepository.getApod()).thenReturn(Observable.error(NetworkErrorException()))

        mainDaydreamServiceViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
        verify(mainDaydreamServiceView).showLoadingError()
    }

    @Test
    fun shouldLoadImageWhenRetrievingDataOnDreamingStartedSucceeds() {
        val mainDreamServiceViewModel = MainDaydreamServiceViewModel(imageUrl = "url", title = "title", description = "My description", originalUrl = "url")

        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))
        `when`(apodDataMapper.apodDTOToMainDaydreamViewModel(APOD_DTO)).thenReturn(mainDreamServiceViewModel)


        mainDaydreamServiceViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
        verify(mainDaydreamServiceView).loadImage("url")
    }

    @Test
    fun shouldLoadDataOnApodImageReady() {
        val mainDreamServiceViewModel = MainDaydreamServiceViewModel(imageUrl = "url", title = "title", description = "My description", originalUrl = "url")
        mainDaydreamServiceViewPresenter.mainDaydreamServiceViewModelState = mainDreamServiceViewModel

        mainDaydreamServiceViewPresenter.onApodImageReady()

        verify(mainDaydreamServiceView).loadTitle("title")
        verify(mainDaydreamServiceView).loadDescription("My description")
    }

    @Test
    fun shouldShowErrorOnApodImageException() {
        mainDaydreamServiceViewPresenter.onApodImageException()

        verify(mainDaydreamServiceView).showError()
    }

    @Test
    fun shouldOpenExternalLinkIfMediaTypeisVideoAndKeyEventIsAnEnter() {
        val mainDreamServiceViewModel = MainDaydreamServiceViewModel(imageUrl = "url", title = "title", description = "My description", originalUrl = "originalUrl")

        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))
        `when`(apodDataMapper.apodDTOToMainDaydreamViewModel(APOD_DTO)).thenReturn(mainDreamServiceViewModel)

        //Enter
        val event = mock(KeyEvent::class.java)
        `when`(event.action).thenReturn(KeyEvent.ACTION_UP)
        `when`(event.keyCode).thenReturn(KeyEvent.KEYCODE_DPAD_CENTER)

        mainDaydreamServiceViewPresenter.onDreamingStarted()
        mainDaydreamServiceViewPresenter.onDispatchKeyEvent(event)

        verify(mainDaydreamServiceView).openYoutubeVideo("originalUrl")
    }

    @Test
    fun onOpenYoutubeVideoShouldShowErrorMessageIfUriFailsToParse() {
        `when`(mainDaydreamServiceView.parseUri("dda2323//22")).thenThrow(Exception::class.java)

        mainDaydreamServiceViewPresenter.onOpenYoutubeVideo("dda2323//22")

        verify(mainDaydreamServiceView).showError()
    }

    @Test
    fun onOpenYoutubeVideoShouldShowErrorMessageIfExtractIdFailed() {
        mainDaydreamServiceViewPresenter.onOpenYoutubeVideo("https://www.youtube.com/watch?invalidParam=Q3VjaCy5gck")

        verify(mainDaydreamServiceView).showError()
    }

    @Test
    fun onOpenYoutubeVideoShouldOpenUsingExplicityIntent() {
        val expectedUri = mock(Uri::class.java)
        `when`(mainDaydreamServiceView.parseUri("vnd.youtube:" + "youtubeId")).thenReturn(expectedUri)
        `when`(mainDaydreamServiceView.parseUri("https://www.youtube.com/watch?v=youtubeId")).thenReturn(expectedUri)

        mainDaydreamServiceViewPresenter.onOpenYoutubeVideo("https://www.youtube.com/watch?v=youtubeId")

        verify(mainDaydreamServiceView).openExplictIntentVideo(expectedUri)
        verify(mainDaydreamServiceView, never()).openImplictIntentVideo(expectedUri)
    }

    @Test
    fun onOpenYoutubeVideoShouldOpenUsingImplicitIntentIfExplicityIntentFails() {
        val expectedUri = mock(Uri::class.java)
        `when`(mainDaydreamServiceView.parseUri("vnd.youtube:" + "youtubeId")).thenReturn(expectedUri)
        `when`(mainDaydreamServiceView.parseUri("https://www.youtube.com/watch?v=youtubeId")).thenReturn(expectedUri)
        `when`(mainDaydreamServiceView.openExplictIntentVideo(ArgumentMatchers.any())).thenThrow(ActivityNotFoundException::class.java)

        mainDaydreamServiceViewPresenter.onOpenYoutubeVideo("https://www.youtube.com/watch?v=youtubeId")

        verify(mainDaydreamServiceView).openExplictIntentVideo(ArgumentMatchers.any())
        verify(mainDaydreamServiceView).openImplictIntentVideo(expectedUri)
    }
}