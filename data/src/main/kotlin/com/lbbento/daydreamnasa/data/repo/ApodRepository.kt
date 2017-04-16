package com.lbbento.daydreamnasa.data.repo

import com.lbbento.daydreamnasa.data.api.apod.ApodDTO
import rx.Observable

interface ApodRepository {
    fun getApod(): Observable<ApodDTO>
}
