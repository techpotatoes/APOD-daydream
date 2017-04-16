package com.lbbento.daydreamnasa.data.repo

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import com.lbbento.daydreamnasa.data.repo.source.ApodLocalRepository
import com.lbbento.daydreamnasa.data.repo.source.ApodRemoteRepository
import rx.Observable
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(val remoteRepoSource : ApodRemoteRepository,
                                             val localRepoSource : ApodLocalRepository) : ApodRepository {

    override fun getApod(): Observable<ApodDTO> {
        return localRepoSource.getApod()
                .flatMap { apodDTO ->
                    if (apodDTO != null) {
                        Observable.just(apodDTO)
                    } else {
                        remoteRepoSource.getApod().doOnNext { apodRemote -> localRepoSource.setApod(apodRemote)}
                    }
                }
    }
}
