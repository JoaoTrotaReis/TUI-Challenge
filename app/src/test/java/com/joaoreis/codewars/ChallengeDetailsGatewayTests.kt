package com.joaoreis.codewars

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.joaoreis.codewars.api.CodewarsAPI
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetails
import com.joaoreis.codewars.challengedetails.data.HttpChallengeDetailsGateway
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit

class ChallengeDetailsGatewayTests {

    @Test
    fun `Given the challenge details are available in the API When challenge details are requested Then return challenge details`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(TestData.CHALLENGE_DETAILS))

        val codewarsAPI = createCodewarsAPI(server.url("/").toString())

        val expectedResult = Result.Success(
            ChallengeDetails(
                name = "Valid Braces",
                description = "Write a function called `validBraces` that takes a string ...",
                tags = listOf("Algorithms", "Validation", "Logic", "Utilities"),
                languages = listOf("javascript", "coffeescript")
            )
        )

        val gateway = HttpChallengeDetailsGateway(
            codewarsAPI = codewarsAPI,
            dispatcher = dispatcher
        )

        assertEquals(expectedResult, gateway.getChallengeDetails("id"))

        val request = server.takeRequest()

        assertEquals("/code-challenges/id", request.path.toString())
        assertEquals("GET", request.method.toString())

        server.shutdown()
    }

    @Test
    fun `Given there is an error fetching the challenge details from the API When the challenge details are requested Then return error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))

        val codewarsAPI = createCodewarsAPI(server.url("/").toString())

        val gateway = HttpChallengeDetailsGateway(
            codewarsAPI = codewarsAPI,
            dispatcher = dispatcher
        )

        assertTrue(gateway.getChallengeDetails("id") is Result.Error)

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