package com.example.apiintergrationwithcleanarch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiintergrationwithcleanarch.domain.model.Post
import com.example.apiintergrationwithcleanarch.domain.usecase.PostUseCase
import com.example.apiintergrationwithcleanarch.presentation.screens.PostUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postUseCase: PostUseCase) : ViewModel() {
    val postUiState: Flow<PostUiState<List<Post>>> =
        flow {
            emitAll(
                postUseCase().map { data ->
                    if (data.isEmpty())
                        PostUiState.Empty
                    else
                        PostUiState.Success(data)
                }.onStart { emit(PostUiState.Loading) }.catch { e ->
                    emit(PostUiState.Error(e))
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = PostUiState.Loading
                )
            )
        }
}