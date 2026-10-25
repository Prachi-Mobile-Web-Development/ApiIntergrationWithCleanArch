package com.example.apiintergrationwithcleanarch.data.mapper

import com.example.apiintergrationwithcleanarch.data.dto.PostDto
import com.example.apiintergrationwithcleanarch.domain.model.Post

fun PostDto.toDomain(): Post =
    Post(
        userId = userId,
        id = id,
        title = title,
        body = body
    )