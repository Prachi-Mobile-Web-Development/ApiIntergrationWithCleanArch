package com.example.apiintergrationwithcleanarch.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.apiintergrationwithcleanarch.domain.model.Post
import com.example.apiintergrationwithcleanarch.domain.usecase.PostUseCase
import com.example.apiintergrationwithcleanarch.presentation.screens.PostUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postUseCase: PostUseCase) : ViewModel() {
   private val _postUiState = MutableStateFlow<PostUiState<List<Post>>>(
       PostUiState.Loading)
    val postUiState= _postUiState.asStateFlow()

    fun fetchPosts(){
        viewModelScope.launch {
            _postUiState.value= PostUiState.Loading
            postUseCase().onSuccess {data->
                if(data.isEmpty())
                {
                    _postUiState.value= PostUiState.Empty
            }
                else{
                    _postUiState.value= PostUiState.Success(data)
                }

            }.onFailure {e->
                _postUiState.value= PostUiState.Error(e)
            }
        }
    }
}