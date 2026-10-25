package com.example.apiintergrationwithcleanarch.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.apiintergrationwithcleanarch.domain.model.Post

@Composable
fun PostListScreen(posts: List<Post>) {
    LazyColumn() {
        items(
            items = posts, key = { it.id }
        ) { post ->
            PostItem(post)
        }
    }

}

@Composable
fun PostItem(post: Post) {
    Column {
        Text(post.id.toString(), style = MaterialTheme.typography.titleMedium)
        Text(post.title, style = MaterialTheme.typography.titleSmall)

    }
}