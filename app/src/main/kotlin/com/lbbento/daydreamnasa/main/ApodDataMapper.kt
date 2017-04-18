package com.lbbento.daydreamnasa.main

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import java.lang.String.format
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class ApodDataMapper @Inject constructor() {
    val APOD_MEDIA_TYPE_VIDEO = "video"

    fun apodDTOToMainDaydreamViewModel(apodDTO: ApodDTO) : MainDaydreamServiceViewModel{
        return MainDaydreamServiceViewModel(imageUrl = parseMedia(apodDTO.media_type, apodDTO.hdurl), title = apodDTO.title, description = apodDTO.explanation)
    }

    private fun parseMedia(mediaType: String, hdurl: String): String {
        when(mediaType) {
            APOD_MEDIA_TYPE_VIDEO -> { return extractThumbnail(hdurl) }
        }
        return hdurl
    }

    private fun extractThumbnail(hdurl: String): String {
        if (hdurl.contains("youtube")) {
            return format("https://img.youtube.com/vi/%s/hqdefault.jpg", extractYoutubeId(hdurl))
        }
        return ""
    }

    @Throws(MalformedURLException::class)
    private fun extractYoutubeId(url: String): String {
        val query = URL(url).getQuery()
        val param = query.split("&".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        var id: String = ""
        param
                .map { row -> row.split("=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray() }
                .filter { it[0] == "v" }
                .forEach { id = it[1] }
        return id
    }

}