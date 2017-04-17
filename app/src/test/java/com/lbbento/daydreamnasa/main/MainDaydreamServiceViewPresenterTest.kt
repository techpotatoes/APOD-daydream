package com.lbbento.daydreamnasa.main

import android.accounts.NetworkErrorException
import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.data.repo.ApodRepository
import com.lbbento.daydreamnasa.di.AppSchedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
    lateinit var mainDaydreamServiceViewContract: MainDaydreamServiceViewContract

    @InjectMocks
    lateinit var mainDaydreamServiceViewPresenter: MainDaydreamServiceViewPresenter

    private val APOD_DTO = ApodDTO(date = Date(), explanation = "My description", hdurl = "url", copyright = "", media_type = "", service_version = "", title = "title", url = "")

    @Before
    fun setup() {
        `when`(appSchedulers.io()).thenReturn(ImmediateScheduler.INSTANCE)
        `when`(appSchedulers.ui()).thenReturn(ImmediateScheduler.INSTANCE)

        mainDaydreamServiceViewPresenter.onAttachedToWindow(mainDaydreamServiceViewContract)
    }

    @Test
    fun shouldRetrieveDataOnDreamingStarted() {
        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))

        mainDaydreamServiceViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
    }

    @Test
    fun shouldShowAndHideSpinnerWhenRetrievingDataOnDreamingStarted() {
        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))

        mainDaydreamServiceViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
        verify(mainDaydreamServiceViewContract).showLoading()
        verify(mainDaydreamServiceViewContract).hideLoading()
    }

    @Test
    fun shouldShowErrorAndHideLoadingWhenRetrievingDataOnDreamingStartedFails() {
        `when`(apodRepository.getApod()).thenReturn(Observable.error(NetworkErrorException()))

        mainDaydreamServiceViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
        verify(mainDaydreamServiceViewContract).showLoading()
        verify(mainDaydreamServiceViewContract).hideLoading()
        verify(mainDaydreamServiceViewContract).showError()
    }

    @Test
    fun shouldLoadImageAndDataWhenRetrievingDataOnDreamingStartedSucceeds() {
        `when`(apodRepository.getApod()).thenReturn(Observable.just(APOD_DTO))

        val mainDreamServiceViewModel = MainDaydreamServiceViewModel(imageUrl = "url", title = "title", description = "My description")

        mainDaydreamServiceViewPresenter.onDreamingStarted()

        verify(apodRepository).getApod()
        verify(mainDaydreamServiceViewContract).showLoading()
        verify(mainDaydreamServiceViewContract).hideLoading()
        verify(mainDaydreamServiceViewContract).loadContent(mainDreamServiceViewModel)
    }
}