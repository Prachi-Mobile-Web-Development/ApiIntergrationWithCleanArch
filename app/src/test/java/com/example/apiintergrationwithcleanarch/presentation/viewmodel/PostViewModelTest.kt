package com.example.apiintergrationwithcleanarch.presentation.viewmodel

import app.cash.turbine.test
import com.example.apiintergrationwithcleanarch.domain.model.Post
import com.example.apiintergrationwithcleanarch.domain.usecase.PostUseCase
import com.example.apiintergrationwithcleanarch.presentation.screens.PostUiState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(JUnitParamsRunner::class)
class PostViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val postUseCase: PostUseCase = mockk()
    private lateinit var postViewModel: PostViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    @Parameters(method = "getAllParameters")
    fun `Given UseCase response when collecting postUiState then verify state`(
        isSuccess: Boolean,
    ) {
        runTest {
            //Given

           val post = listOf(
                Post(
                    1,
                    1,
                    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                )
            )

            val exception= RuntimeException("Something went wrong")

            coEvery { postUseCase() } returns if(isSuccess)
             {Result.success(post)
            }
            else{
                Result.failure(exception)
            }
            postViewModel = PostViewModel(postUseCase)
            //When
             postViewModel.fetchPosts()
            //Then
            val state = postViewModel.postUiState.value
            if(isSuccess){
                assertTrue( state is PostUiState.Success)
                assertEquals(post,(state as PostUiState.Success).data)
            }
            else{
                assertTrue(state is PostUiState.Error)
                assertEquals(exception,(state as PostUiState.Error).throwable)
            }
            coVerify(exactly = 1) { postUseCase() }
        }
    }

    fun getAllParameters() = arrayOf(
        arrayOf(true),
        arrayOf(false)
    )

}