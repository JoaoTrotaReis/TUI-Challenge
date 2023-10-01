package com.joaoreis.codewars

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.joaoreis.codewars.api.CodewarsAPI
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenge
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenges
import com.joaoreis.codewars.completedchallenges.data.CompletedChallengeDTO
import com.joaoreis.codewars.completedchallenges.data.HttpCompletedChallengesGateway
import com.joaoreis.codewars.completedchallenges.data.CompletedChallengesListDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import java.io.IOException

class CompletedChallengesGatewayTests {

    @Test
    fun `Given there are completed challenges in the API When completed challenges are requested Then return the list of completed challenges`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody(TestData.COMPLETED_CHALLENGES))

            val codewarsAPI = createCodewarsAPI(server.url("/").toString())

            val expectedResult = Result.Success(
                CompletedChallenges(
                    currentPage = 1,
                    totalPages = 1,
                    challenges = listOf(
                        CompletedChallenge(
                            id = "514b92a657cdc65150000006",
                            name = "Multiples of 3 and 5",
                            completedAt = Instant.parse("2017-04-06T16:32:09Z"),
                            languages = listOf("javascript")
                        )
                    )
                )
            )

            val gateway = HttpCompletedChallengesGateway(
                codewarsAPI = codewarsAPI,
                dispatcher = dispatcher
            )

            val actualResult = gateway.getCompletedChallenges("name", 1)

            val request = server.takeRequest()
            assertEquals(expectedResult, actualResult)
            assertEquals("/users/name/code-challenges/completed?page=1", request.path.toString())
            assertEquals("GET", request.method.toString())

            server.shutdown()
        }

    @Test
    fun `Given there is a problem fetching completed challenges from the API When completed challenges are requested Then return error`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setResponseCode(404))

            val codewarsAPI = createCodewarsAPI(server.url("/").toString())

            val gateway = HttpCompletedChallengesGateway(
                codewarsAPI = codewarsAPI,
                dispatcher = dispatcher
            )

            val actualResult = gateway.getCompletedChallenges("name", 1)

            assert(actualResult is Result.Error)

            server.shutdown()
        }

    private fun createCodewarsAPI(baseUrl: String): CodewarsAPI {
        return Retrofit.Builder()
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder().build())
            .build()
            .create(CodewarsAPI::class.java)
    }
}