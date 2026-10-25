package com.example.apiintergrationwithcleanarch.data.repositoryImp

import com.example.apiintergrationwithcleanarch.data.datasource.FetchPostDataSource
import com.example.apiintergrationwithcleanarch.data.mapper.toDomain
import com.example.apiintergrationwithcleanarch.domain.model.Post
import com.example.apiintergrationwithcleanarch.domain.repository.FetchPostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchPostRepositoryImpl @Inject constructor(private val fetchPostDataSource: FetchPostDataSource) :
    FetchPostsRepository {
    override suspend fun fetchPosts(): Flow<List<Post>> = flow {
        val data = fetchPostDataSource.fetchPosts().map { it.toDomain() }
        emit(data)
    }.flowOn(Dispatchers.IO)
}