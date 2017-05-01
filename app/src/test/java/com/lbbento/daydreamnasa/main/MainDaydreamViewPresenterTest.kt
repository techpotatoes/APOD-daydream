package com.lbbento.daydreamnasa.main

import android.accounts.NetworkErrorException
import android.content.ActivityNotFoundException
import android.net.Uri
import android.view.KeyEvent
import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.data.repo.ApodRepository
import com.lbbento.daydreamnasa.di.AppSchedulers
import com.lbbento.daydreamnasa.main.model.MainDaydreamDataMapper
import com.lbbento.daydreamnasa.main.model.MainDaydreamViewModel
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
class MainDaydreamViewPresenterTest {

    @Mock
    lateinit var mainDaydreamDataMapper: MainDaydreamDataMapper

    @Mock
    lateinit var apodRepository : ApodRepository

    @Mock
    lateinit var appSchedulers : AppSchedulers

    @Mock
    lateinit var mainDaydreamView: MainDaydreamViewContract

    @InjectMocks
    lateinit var mainDaydreamViewPresenter: MainDaydreamViewPresenter

    private val APOD_DTO = ApodDTO(date = Date(), explanation = "My description", hdurl = "url", copyright = "", media_type = "", service_version = "", title = "title", url = "")

    @Before
    fun setup() {
        `when`(appSchedulers.io()).thenReturn(ImmediateScheduler.INSTANCE)
        `when`(appSchedulers.ui()).thenReturn(ImmediateScheduler.INSTANCE)

        mainDaydreamViewPresenter.onAttachedToWindow(mainDaydreamView)
    }

    @Test
    fun shouldRetrieveDataOnDreamingStarted() {
        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))

        mainDaydreamViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
    }
    @Test
    fun shouldShowErrorWhenRetrievingDataOnDreamingStartedFails() {
        `when`(apodRepository.getApod()).thenReturn(Observable.error(NetworkErrorException()))

        mainDaydreamViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
        verify(mainDaydreamView).showLoadingError()
    }

    @Test
    fun shouldLoadImageWhenRetrievingDataOnDreamingStartedSucceeds() {
        val mainDreamServiceViewModel = MainDaydreamViewModel(imageUrl = "url", title = "title", description = "My description", originalUrl = "url")

        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))
        `when`(mainDaydreamDataMapper.apodDTOToMainDaydreamViewModel(APOD_DTO)).thenReturn(mainDreamServiceViewModel)


        mainDaydreamViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
        verify(mainDaydreamView).loadImage("url")
    }

    @Test
    fun shouldLoadDataOnApodImageReady() {
        val mainDreamServiceViewModel = MainDaydreamViewModel(imageUrl = "url", title = "title", description = "My description", originalUrl = "url")
        mainDaydreamViewPresenter.mainDaydreamViewModelState = mainDreamServiceViewModel

        mainDaydreamViewPresenter.onApodImageReady()

        verify(mainDaydreamView).loadTitle("title")
        verify(mainDaydreamView).loadDescription("My description")
    }

    @Test
    fun shouldShowVideoLinkIfMediaIsVideoOnApodImageReady() {
        val mainDreamServiceViewModel = MainDaydreamViewModel(imageUrl = "url", title = "title", description = "My description", originalUrl = "url", mediaType = "video")
        mainDaydreamViewPresenter.mainDaydreamViewModelState = mainDreamServiceViewModel

        mainDaydreamViewPresenter.onApodImageReady()

        verify(mainDaydreamView).showVideoLink(true)
    }

    @Test
    fun shouldNotShowVideoLinkIfMediaIsNotVideoOnApodImageReady() {
        val mainDreamServiceViewModel = MainDaydreamViewModel(imageUrl = "url", title = "title", description = "My description", originalUrl = "url", mediaType = "image")
        mainDaydreamViewPresenter.mainDaydreamViewModelState = mainDreamServiceViewModel

        mainDaydreamViewPresenter.onApodImageReady()

        verify(mainDaydreamView).showVideoLink(false)
    }

    @Test
    fun shouldShowErrorOnApodImageException() {
        mainDaydreamViewPresenter.onApodImageException()

        verify(mainDaydreamView).showError()
    }

    @Test
    fun shouldOpenExternalLinkIfMediaTypeisVideoAndKeyEventIsAnEnter() {
        val mainDreamServiceViewModel = MainDaydreamViewModel(imageUrl = "url", title = "title", description = "My description", originalUrl = "originalUrl", mediaType = "video")

        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))
        `when`(mainDaydreamDataMapper.apodDTOToMainDaydreamViewModel(APOD_DTO)).thenReturn(mainDreamServiceViewModel)

        val event = mock(KeyEvent::class.java)
        `when`(event.action).thenReturn(KeyEvent.ACTION_UP)
        `when`(event.keyCode).thenReturn(KeyEvent.KEYCODE_DPAD_CENTER)

        mainDaydreamViewPresenter.onDreamingStarted()
        mainDaydreamViewPresenter.onKeyEvent(event)

        verify(mainDaydreamView).openYoutubeVideo("originalUrl")
    }

    @Test
    fun shouldFinishViewIfKeyEventIsABack() {
        val event = mock(KeyEvent::class.java)
        `when`(event.action).thenReturn(KeyEvent.ACTION_UP)
        `when`(event.keyCode).thenReturn(KeyEvent.KEYCODE_BACK)

        mainDaydreamViewPresenter.onKeyEvent(event)

        verify(mainDaydreamView).finish()
    }

    @Test
    fun onTouchShouldFinishTheViewIfKeyEventIsBack() {
        mainDaydreamViewPresenter.onTouchEvent()

        verify(mainDaydreamView).finish()
    }

    @Test
    fun onOpenYoutubeVideoShouldShowErrorMessageIfUriFailsToParse() {
        `when`(mainDaydreamView.parseUri("dda2323//22")).thenThrow(Exception::class.java)

        mainDaydreamViewPresenter.onOpenYoutubeVideo("dda2323//22")

        verify(mainDaydreamView).showError()
    }

    @Test
    fun onOpenYoutubeVideoShouldShowErrorMessageIfExtractIdFailed() {
        mainDaydreamViewPresenter.onOpenYoutubeVideo("https://www.youtube.com/watch?invalidParam=Q3VjaCy5gck")

        verify(mainDaydreamView).showError()
    }

    @Test
    fun onOpenYoutubeVideoShouldOpenUsingExplicityIntent() {
        val expectedUri = mock(Uri::class.java)
        `when`(mainDaydreamView.parseUri("vnd.youtube:" + "youtubeId")).thenReturn(expectedUri)
        `when`(mainDaydreamView.parseUri("https://www.youtube.com/watch?v=youtubeId")).thenReturn(expectedUri)

        mainDaydreamViewPresenter.onOpenYoutubeVideo("https://www.youtube.com/watch?v=youtubeId")

        verify(mainDaydreamView).openExplictIntentVideo(expectedUri)
        verify(mainDaydreamView, never()).openImplictIntentVideo(expectedUri)
    }

    @Test
    fun onOpenYoutubeVideoShouldOpenUsingImplicitIntentIfExplicityIntentFails() {
        val expectedUri = mock(Uri::class.java)
        `when`(mainDaydreamView.parseUri("vnd.youtube:" + "youtubeId")).thenReturn(expectedUri)
        `when`(mainDaydreamView.parseUri("https://www.youtube.com/watch?v=youtubeId")).thenReturn(expectedUri)
        `when`(mainDaydreamView.openExplictIntentVideo(ArgumentMatchers.any())).thenThrow(ActivityNotFoundException::class.java)

        mainDaydreamViewPresenter.onOpenYoutubeVideo("https://www.youtube.com/watch?v=youtubeId")

        verify(mainDaydreamView).openExplictIntentVideo(ArgumentMatchers.any())
        verify(mainDaydreamView).openImplictIntentVideo(expectedUri)
    }
}