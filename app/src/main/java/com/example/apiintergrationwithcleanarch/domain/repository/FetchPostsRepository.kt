package com.example.apiintergrationwithcleanarch.domain.repository

import com.example.apiintergrationwithcleanarch.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface FetchPostsRepository {
    suspend fun fetchPosts(): Flow<List<Post>>
}