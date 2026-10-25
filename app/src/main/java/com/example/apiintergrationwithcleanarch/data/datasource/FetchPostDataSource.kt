package com.example.apiintergrationwithcleanarch.data.datasource

import com.example.apiintergrationwithcleanarch.data.dto.PostDto

interface FetchPostDataSource {
    suspend fun fetchPosts(): List<PostDto>
}