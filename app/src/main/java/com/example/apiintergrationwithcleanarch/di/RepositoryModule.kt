package com.example.apiintergrationwithcleanarch.di

import com.example.apiintergrationwithcleanarch.data.repositoryImp.FetchPostRepositoryImpl
import com.example.apiintergrationwithcleanarch.domain.repository.FetchPostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)abstract class RepositoryModule {
    @Binds
    abstract fun provideRepositoryModule(fetchPostRepositoryImpl: FetchPostRepositoryImpl): FetchPostsRepository
}