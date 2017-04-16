package com.lbbento.daydreamnasa.data.repo.source

import com.lbbento.daydreamnasa.data.api.apod.ApodApiProvider
import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import rx.observers.TestSubscriber
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ApodRemoteRepositoryTest {

    @Mock
    lateinit var apodApiProvider : ApodApiProvider

    val APOD_DTO = ApodDTO(date = Date(), explanation = "", hdurl = "", copyright = "", media_type = "", service_version = "", title = "", url = "")

    @Test(expected = UnsupportedOperationException::class)
    fun shouldThrowExceptionWhenTryingToSetApod() {
        val apodRemoteRepository = ApodRemoteRepository(apodApiProvider)

        apodRemoteRepository.setApod(APOD_DTO)
    }

    @Test
    fun shouldGetApodDTOFromProviderOnGetApodDTO() {
        val apodRemoteRepository = ApodRemoteRepository(apodApiProvider)

        `when`(apodApiProvider.getApod()).thenReturn(Observable.just(APOD_DTO))

        val testSubscriber = TestSubscriber<ApodDTO>()
        apodRemoteRepository.getApod().subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        verify(apodApiProvider).getApod()
        assertEquals(testSubscriber.onNextEvents[0], APOD_DTO)
    }


}