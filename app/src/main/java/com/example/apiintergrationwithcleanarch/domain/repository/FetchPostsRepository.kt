package com.example.apiintergrationwithcleanarch.domain.repository

import com.example.apiintergrationwithcleanarch.domain.model.Post

interface FetchPostsRepository {
    suspend fun fetchPosts(): Result<List<Post>>
}