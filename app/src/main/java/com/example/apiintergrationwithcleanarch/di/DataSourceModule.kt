package com.example.apiintergrationwithcleanarch.di

import com.example.apiintergrationwithcleanarch.data.datasource.FetchPostDataSource
import com.example.apiintergrationwithcleanarch.data.datasourceimpl.FetchPostDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun provideDataSource(fetchPostDataSourceImpl: FetchPostDataSourceImpl): FetchPostDataSource
}