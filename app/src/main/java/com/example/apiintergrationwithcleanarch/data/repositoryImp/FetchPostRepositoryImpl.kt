package com.example.apiintergrationwithcleanarch.data.repositoryImp

import com.example.apiintergrationwithcleanarch.data.mapper.toDomain
import com.example.apiintergrationwithcleanarch.data.remote.PostApiService
import com.example.apiintergrationwithcleanarch.domain.model.Post
import com.example.apiintergrationwithcleanarch.domain.repository.FetchPostsRepository
import javax.inject.Inject

class FetchPostRepositoryImpl @Inject constructor(private val postApiService: PostApiService) :
    FetchPostsRepository {
    override suspend fun fetchPosts(): Result<List<Post>> {
        return try {
            val data = postApiService.fetchPosts().map { it.toDomain() }
             Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}



