package com.example.apiintergrationwithcleanarch.data.datasourceimpl

import com.example.apiintergrationwithcleanarch.data.dto.PostDto
import com.example.apiintergrationwithcleanarch.data.remote.PostApiService
import com.example.apiintergrationwithcleanarch.data.repositoryImp.FetchPostRepositoryImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class FetchPostDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val postApiService: PostApiService= mockk()
    private lateinit var classToTest: FetchPostDataSourceImpl
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
   fun `Given api service response when fetchPosts is called then return api response`(input:List<PostDto>){
       runTest {
           coEvery { postApiService.fetchPosts() } returns input
           classToTest = FetchPostDataSourceImpl(postApiService)
           val result = classToTest.fetchPosts()
           assertEquals(result,input)
           coVerify(exactly = 1) { postApiService.fetchPosts() }
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