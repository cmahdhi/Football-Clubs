package com.chiheb.footballclubs.core.di

import com.chiheb.footballclubs.data.repositories.FootballClubsRepositoryImpl
import com.chiheb.footballclubs.domain.repositories.IFootballClubsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindFootballClubsRepository(impl: FootballClubsRepositoryImpl): IFootballClubsRepository
}