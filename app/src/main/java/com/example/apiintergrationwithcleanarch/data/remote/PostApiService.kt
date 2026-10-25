package com.example.apiintergrationwithcleanarch.data.remote

import com.example.apiintergrationwithcleanarch.data.dto.PostDto
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun fetchPosts(): List<PostDto>
}