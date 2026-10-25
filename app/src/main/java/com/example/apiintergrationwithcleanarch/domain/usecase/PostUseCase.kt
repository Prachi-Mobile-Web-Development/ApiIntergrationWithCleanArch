package com.example.apiintergrationwithcleanarch.domain.usecase

import com.example.apiintergrationwithcleanarch.domain.repository.FetchPostsRepository
import javax.inject.Inject

class PostUseCase @Inject constructor(private val fetchPostsRepository: FetchPostsRepository) {
    suspend operator fun invoke() =
        fetchPostsRepository.fetchPosts()
}