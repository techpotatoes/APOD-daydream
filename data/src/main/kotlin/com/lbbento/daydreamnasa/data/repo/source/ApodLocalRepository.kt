package com.lbbento.daydreamnasa.data.repo.source

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApodLocalRepository @Inject constructor() : ApodRepoSource {

    var cachedApodDTO : ApodDTO? = null

    override fun getApod(): Observable<ApodDTO> {
        return Observable.just(cachedApodDTO)
    }

    override fun setApod(apodDTO: ApodDTO) {
        this.cachedApodDTO = apodDTO
    }

}
