package com.lbbento.daydreamnasa.main

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApodDataMapper @Inject constructor() {

    fun apodDTOToMainDaydreamViewModel(apodDTO: ApodDTO) : MainDaydreamServiceViewModel{
        return MainDaydreamServiceViewModel(imageUrl = apodDTO.hdurl, title = apodDTO.title, description = apodDTO.explanation)
    }

}