package com.lbbento.daydreamnasa.di

import android.app.Application
import android.content.Context
import com.lbbento.daydreamnasa.data.api.ServiceGenerator
import com.lbbento.daydreamnasa.data.api.apod.ApodApiProvider
import com.lbbento.daydreamnasa.data.repo.ApodRepository
import com.lbbento.daydreamnasa.data.repo.ApodRepositoryImpl
import com.lbbento.daydreamnasa.data.repo.source.ApodLocalRepository
import com.lbbento.daydreamnasa.data.repo.source.ApodRemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideApodApiProvider(): ApodApiProvider {
        return ServiceGenerator.createService(ApodApiProvider::class.java)
    }

    @Provides
    @Singleton
    fun provideApodRepository(apodLocalRepository: ApodLocalRepository,
                              apodRemoteRepository: ApodRemoteRepository): ApodRepository {
        return ApodRepositoryImpl(apodRemoteRepository, apodLocalRepository)
    }

}