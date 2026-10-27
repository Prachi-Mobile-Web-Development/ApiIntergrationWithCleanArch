package com.example.apiintergrationwithcleanarch.presentation.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apiintergrationwithcleanarch.domain.model.Post
import com.example.apiintergrationwithcleanarch.presentation.viewmodel.PostViewModel

@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    val uiState by viewModel.postUiState.collectAsStateWithLifecycle(PostUiState.Loading)
    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }
    when(val post=uiState) {
        is PostUiState.Empty -> {
            Text("No Data")
        }
        is PostUiState.Error ->
        {
            Text("${post.throwable.message}")
        }
        is PostUiState.Loading -> {
            CircularProgressIndicator()
        }
        is PostUiState.Success -> {
            PostListScreen(post.data)
        }
    }
}