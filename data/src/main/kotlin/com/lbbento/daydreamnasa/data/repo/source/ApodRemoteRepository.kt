package com.lbbento.daydreamnasa.data.repo.source

import com.lbbento.daydreamnasa.data.api.apod.ApodApiProvider
import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApodRemoteRepository @Inject constructor(val apodApiService: ApodApiProvider) : ApodRepoSource {

    override fun getApod(): Observable<ApodDTO> {
        return apodApiService.getApod()
    }

    override fun setApod(apodDTO: ApodDTO) {
        throw UnsupportedOperationException()
    }

}