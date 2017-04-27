package com.lbbento.daydreamnasa.main

import com.lbbento.daydreamnasa.AppUtil
import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import java.lang.String.format
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class ApodDataMapper @Inject constructor() {
    val APOD_MEDIA_TYPE_VIDEO = "video"

    open fun apodDTOToMainDaydreamViewModel(apodDTO: ApodDTO) : MainDaydreamServiceViewModel{
        return MainDaydreamServiceViewModel(imageUrl = parseMedia(apodDTO.media_type, apodDTO.hdurl), title = apodDTO.title, description = apodDTO.explanation, originalUrl = apodDTO.hdurl, mediaType = apodDTO.media_type)
    }

    private fun parseMedia(mediaType: String, hdurl: String): String {
        when(mediaType) {
            APOD_MEDIA_TYPE_VIDEO -> { return extractThumbnail(hdurl) }
        }
        return hdurl
    }

    private fun extractThumbnail(hdurl: String): String {
        if (hdurl.contains("youtube")) {
            return format("https://img.youtube.com/vi/%s/hqdefault.jpg", AppUtil.extractYoutubeId(hdurl))
        }
        return ""
    }
}