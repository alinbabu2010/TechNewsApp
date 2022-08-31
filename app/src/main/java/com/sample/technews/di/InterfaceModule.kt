package com.sample.technews.di

import com.sample.technews.data.sources.remote.NewsApiSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface InterfaceModule {

    @Binds
    fun bindNewsRemoteDataSource(
        newsApiSource: NewsApiSource
    ): NewsApiSource

}