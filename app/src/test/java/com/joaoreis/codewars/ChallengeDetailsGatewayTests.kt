package com.joaoreis.codewars

import com.joaoreis.codewars.api.CodewarsAPI
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetails
import com.joaoreis.codewars.challengedetails.data.ChallengeDetailsDTO
import com.joaoreis.codewars.challengedetails.data.ChallengeDetailsGatewayImplementation
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ChallengeDetailsGatewayTests {

    @Test
    fun `Given the challenge details are available in the API When challenge details are requested Then return challenge details`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val challengeDetailsDTO = ChallengeDetailsDTO(
            name = "name",
            description = "description",
            tags = listOf("tag"),
            languages = listOf("kotlin")
        )

        val expectedResult = Result.Success(
            ChallengeDetails(
                name = "name",
                description = "description",
                tags = listOf("tag"),
                languages = listOf("kotlin")
            )
        )

        val codewarsAPI = mockk<CodewarsAPI>().also {
            coEvery { it.getChallengeDetails("id") } returns challengeDetailsDTO
        }

        val gateway = ChallengeDetailsGatewayImplementation(
            codewarsAPI = codewarsAPI,
            dispatcher = dispatcher
        )

        assertEquals(expectedResult, gateway.getChallengeDetails("id"))
    }

    @Test
    fun `Given there is an error fetching the challenge details from the API When the challenge details are requested Then return error`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val codewarsAPI = mockk<CodewarsAPI>().also {
            coEvery { it.getChallengeDetails("id") } throws IOException()
        }

        val gateway = ChallengeDetailsGatewayImplementation(
            codewarsAPI = codewarsAPI,
            dispatcher = dispatcher
        )

        assertTrue(gateway.getChallengeDetails("id") is Result.Error)
    }
}