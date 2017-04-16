package com.lbbento.daydreamnasa.data.repo.source

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import rx.Observable

interface ApodRepoSource {
    fun getApod(): Observable<ApodDTO>
    fun setApod(apodDTO: ApodDTO)
}