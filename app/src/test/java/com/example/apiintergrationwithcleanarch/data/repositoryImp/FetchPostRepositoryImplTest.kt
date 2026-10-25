package com.example.apiintergrationwithcleanarch.data.repositoryImp

import app.cash.turbine.test
import com.example.apiintergrationwithcleanarch.data.datasource.FetchPostDataSource
import com.example.apiintergrationwithcleanarch.data.dto.PostDto
import com.example.apiintergrationwithcleanarch.data.mapper.toDomain
import com.example.apiintergrationwithcleanarch.domain.model.Post
import com.example.apiintergrationwithcleanarch.domain.repository.FetchPostsRepository
import io.mockk.coEvery
import io.mockk.coVerify
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
class FetchPostRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val fetchPostDataSource: FetchPostDataSource= mockk()
    private lateinit var classToTest: FetchPostRepositoryImpl

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
    fun `Given datasource response when fetchPost is called then return domain model`(input:List<PostDto>){
        runTest {
            //Given
            coEvery { fetchPostDataSource.fetchPosts() } returns input
            val expected = input.map { it.toDomain() }
            classToTest= FetchPostRepositoryImpl(fetchPostDataSource)
            //When
            classToTest.fetchPosts().test {
                val actual= awaitItem()
                //Then
                assertEquals(expected,actual)
                awaitComplete()
            }
            coVerify(exactly = 1) {  fetchPostDataSource.fetchPosts()}
        }
    }

    fun getAllParameters() = arrayOf(
        arrayOf(
            listOf(
                PostDto(
                    1,
                    1,
                    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                    "quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                )
            )
        )
    )

}