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
    private lateinit var classToTest: PostViewModel

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
        input: List<Post>
    ) {
        runTest {
            //Given
            coEvery { postUseCase() } returns flowOf(input)
            classToTest = PostViewModel(postUseCase)
            //When
            classToTest.postUiState.test {
                //Then
                assertTrue(awaitItem() is PostUiState.Loading)
                val result = awaitItem()
                if (isSuccess) {
                    assertTrue(result is PostUiState.Success)
                    assertEquals(input, (result as PostUiState.Success).data)
                } else {
                    assertTrue(result is PostUiState.Empty)
                }
                cancelAndIgnoreRemainingEvents()
            }
            coVerify(exactly = 1) { postUseCase() }
        }
    }

    fun getAllParameters() = arrayOf(
        arrayOf(
            true, listOf(
                Post(
                    1,
                    1,
                    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                )
            )
        ),
        arrayOf(false, emptyList<Post>())
    )

}