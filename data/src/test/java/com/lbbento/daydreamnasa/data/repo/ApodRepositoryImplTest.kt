package com.lbbento.daydreamnasa.data.repo

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.data.repo.source.ApodLocalRepository
import com.lbbento.daydreamnasa.data.repo.source.ApodRemoteRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import rx.observers.TestSubscriber
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ApodRepositoryImplTest {

    @Mock
    lateinit var apodLocalRepository : ApodLocalRepository

    @Mock
    lateinit var apodRemoteRepository : ApodRemoteRepository

    val APOD_DTO = ApodDTO(date = Date(), explanation = "", hdurl = "", copyright = "", media_type = "", service_version = "", title = "", url = "")
    val REMOTE_APOD_DTO = ApodDTO(date = Date(), explanation = "dsd", hdurl = "ssssds", copyright = "ssdsds", media_type = "", service_version = "", title = "", url = "")

    @Test
    fun shouldReturnApodDTOFromCacheOnGetApod() {
        `when`(apodLocalRepository.getApod()).thenReturn(Observable.just(APOD_DTO))

        val apodRepository = ApodRepositoryImpl(apodRemoteRepository, apodLocalRepository)

        val testSubscriber = TestSubscriber<ApodDTO>()
        apodRepository.getApod().subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        Assert.assertEquals(testSubscriber.onNextEvents[0], APOD_DTO)
    }

    @Test
    fun shouldReturnApodDTOFromRemoteWhenLocalNullOnGetApod() {
        `when`(apodLocalRepository.getApod()).thenReturn(Observable.just(null))
        `when`(apodRemoteRepository.getApod()).thenReturn(Observable.just(REMOTE_APOD_DTO))

        val apodRepository = ApodRepositoryImpl(apodRemoteRepository, apodLocalRepository)

        val testSubscriber = TestSubscriber<ApodDTO>()
        apodRepository.getApod().subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        Assert.assertEquals(testSubscriber.onNextEvents[0], REMOTE_APOD_DTO)
    }

}

