package com.chiheb.footballclubs.core.di

import com.chiheb.footballclubs.data.services.FootballClubsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {

    @Provides
    fun provideCatalogService(retrofit: Retrofit): FootballClubsService =
        retrofit.create(FootballClubsService::class.java)
}