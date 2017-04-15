package com.lbbento.daydreamnasa.data.provider.apod

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ApodApiProvider {

    @GET("apod")
    fun getApod(@Query("api_key") apiKey: String): Observable<ApodDTO>

}
