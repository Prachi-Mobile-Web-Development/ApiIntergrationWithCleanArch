package com.example.apiintergrationwithcleanarch.presentation.screens

sealed class PostUiState<out T> {
    object Loading: PostUiState<Nothing>()
    object Empty: PostUiState<Nothing>()
     class Success<T>(val data:T): PostUiState<T>()
     class Error<T>(val throwable: Throwable): PostUiState<T>()
}