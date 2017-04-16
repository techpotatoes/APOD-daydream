package com.lbbento.daydreamnasa.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    companion object Static {
        val API_BASE_URL = "https://api.nasa.gov/planetary/apod"

        private val gson = GsonBuilder().create()

        private val builder = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))

        fun <S> createService(serviceClass: Class<S>): S {

            val httpClient = OkHttpClient.Builder()
            val client = httpClient.build()

            val retrofit = builder.client(client).build()

            return retrofit.create(serviceClass)
        }
    }


}
