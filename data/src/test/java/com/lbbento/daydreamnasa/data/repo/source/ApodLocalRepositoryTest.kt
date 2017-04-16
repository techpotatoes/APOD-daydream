package com.lbbento.daydreamnasa.data.repo.source

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import rx.observers.TestSubscriber
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ApodLocalRepositoryTest {

    val CACHED_APOD_DTO = ApodDTO(date = Date(), explanation = "", hdurl = "", copyright = "", media_type = "", service_version = "", title = "", url = "")

    @Test
    fun shouldReturnNullIfNotCachedOnGetApod() {
        val apodLocalRepository = ApodLocalRepository()

        val testSubscriber = TestSubscriber<ApodDTO>()
        apodLocalRepository.getApod().subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        assertNull(testSubscriber.onNextEvents[0])
    }

    @Test
    fun shouldReturnCachedApodDTOIfCachedOnGetApod() {
        val apodLocalRepository = ApodLocalRepository()

        apodLocalRepository.setApod(CACHED_APOD_DTO)

        val testSubscriber = TestSubscriber<ApodDTO>()
        apodLocalRepository.getApod().subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        assertEquals(testSubscriber.onNextEvents[0], CACHED_APOD_DTO)
    }

    @Test
    fun shouldSetCachedApodDTOOnSetApodDTO() {
        val apodLocalRepository = ApodLocalRepository()

        apodLocalRepository.setApod(CACHED_APOD_DTO)

        assertEquals(CACHED_APOD_DTO, apodLocalRepository.cachedApodDTO)
    }
}