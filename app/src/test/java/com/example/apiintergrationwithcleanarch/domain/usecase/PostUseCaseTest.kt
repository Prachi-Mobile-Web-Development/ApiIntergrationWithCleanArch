package com.example.apiintergrationwithcleanarch.domain.usecase

import app.cash.turbine.test
import com.example.apiintergrationwithcleanarch.domain.model.Post
import com.example.apiintergrationwithcleanarch.domain.repository.FetchPostsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@RunWith(JUnitParamsRunner::class)
class PostUseCaseTest {
    private val testDispatcher = StandardTestDispatcher()
    private val fetchPostsRepository: FetchPostsRepository = mockk()
    private lateinit var classToTest: PostUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @Parameters(method = "getAllParameters")
    fun `Given repository response when invoking useCase then verify fetchPost result`(expected: List<Post>) {
        runTest {
            //Given
            coEvery {
                fetchPostsRepository.fetchPosts()
            } returns flowOf(expected)
            classToTest = PostUseCase(fetchPostsRepository)
            //When
            classToTest().test {
                //Then
                val success = awaitItem()
                assertEquals(expected, success)
                assertEquals(1, success.size)
                awaitComplete()

            }
        }

    }

    fun getAllParameters() = arrayOf(
        arrayOf(
            listOf(
                Post(
                    1,
                    1,
                    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                )
            )
        )
    )


}