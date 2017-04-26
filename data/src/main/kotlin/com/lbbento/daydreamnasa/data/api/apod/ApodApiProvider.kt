package com.lbbento.daydreamnasa.data.api.apod

import com.lbbento.daydreamnasa.data.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ApodApiProvider {

    @GET("planetary/apod")
    fun getApod(@Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY): Observable<ApodDTO>

}
