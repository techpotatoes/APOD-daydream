package com.lbbento.daydreamnasa.main

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.main.model.MainDaydreamDataMapper
import com.lbbento.daydreamnasa.main.model.MainDaydreamViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainDaydreamDataMapperTest {

    val APOD_DTO = ApodDTO(date = Date(), explanation = "desc", hdurl = "url", copyright = "", media_type = "", service_version = "", title = "title", url = "")
    val MAIN_DREAM_VIEW_MODEL = MainDaydreamViewModel(imageUrl = "url", description = "desc", title = "title", originalUrl = "url")

    val APOD_DTO_YOUTUBE = ApodDTO(date = Date(), explanation = "desc", hdurl = "https://www.youtube.com/watch?v=Q3VjaCy5gck", copyright = "", media_type = "video", service_version = "", title = "title", url = "")
    val MAIN_DREAM_VIEW_MODEL_YOUTUBE = MainDaydreamViewModel(imageUrl = "https://img.youtube.com/vi/Q3VjaCy5gck/hqdefault.jpg", description = "desc", title = "title", originalUrl = "https://www.youtube.com/watch?v=Q3VjaCy5gck", mediaType = "video")


    @Test
    fun shouldConvertapodDTOToMainDaydreamViewModel() {
        val apodDataMapper = MainDaydreamDataMapper()

        assertEquals(apodDataMapper.apodDTOToMainDaydreamViewModel(APOD_DTO), MAIN_DREAM_VIEW_MODEL)
    }

    @Test
    fun shouldGetYoutubeThumbnailIfMediaTypeIsVideo() {
        val apodDataMapper = MainDaydreamDataMapper()

        assertEquals(apodDataMapper.apodDTOToMainDaydreamViewModel(APOD_DTO_YOUTUBE), MAIN_DREAM_VIEW_MODEL_YOUTUBE)
    }
}