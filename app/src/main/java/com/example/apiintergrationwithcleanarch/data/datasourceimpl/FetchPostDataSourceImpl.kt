package com.example.apiintergrationwithcleanarch.data.datasourceimpl

import com.example.apiintergrationwithcleanarch.data.datasource.FetchPostDataSource
import com.example.apiintergrationwithcleanarch.data.dto.PostDto
import com.example.apiintergrationwithcleanarch.data.remote.PostApiService
import javax.inject.Inject

class FetchPostDataSourceImpl @Inject constructor(private val postApiService: PostApiService): FetchPostDataSource {
    override suspend fun fetchPosts(): List<PostDto> {
        return postApiService.fetchPosts()
    }
}