package com.joaoreis.codewars

import com.joaoreis.codewars.api.CodewarsAPI
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenge
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenges
import com.joaoreis.codewars.completedchallenges.data.CompletedChallengeDTO
import com.joaoreis.codewars.completedchallenges.data.CompletedChallengesGatewayImplementation
import com.joaoreis.codewars.completedchallenges.data.CompletedChallengesListDTO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class CompletedChallengesGatewayTests {

    @Test
    fun `Given there are completed challenges in the API When completed challenges are requested Then return the list of completed challenges`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val now = Clock.System.now()
            val completedChallenges = CompletedChallengesListDTO(
                totalItems = 1,
                totalPages = 1,
                data = listOf(
                    CompletedChallengeDTO(
                        id = "id",
                        name = "name",
                        slug = "slug",
                        completedAt = now,
                        completedLanguages = listOf("kotlin")
                    )
                )
            )

            val expectedResult = Result.Success(
                CompletedChallenges(
                    currentPage = 1,
                    totalPages = 1,
                    challenges = listOf(
                        CompletedChallenge(
                            id = "id",
                            name = "name",
                            completedAt = now,
                            languages = listOf("kotlin")
                        )
                    )
                )
            )

            val api = mockk<CodewarsAPI>().also {
                coEvery { it.getCompletedChallenges(any(), any()) } returns completedChallenges
            }

            val gateway = CompletedChallengesGatewayImplementation(
                codewarsAPI = api,
                dispatcher = dispatcher
            )

            val actualResult = gateway.getCompletedChallenges("name", 1)

            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given there is a problem fetching completed challenges from the API When completed challenges are requested Then return error`() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val api = mockk<CodewarsAPI>().also {
                coEvery { it.getCompletedChallenges(any(), any()) } throws IOException()
            }

            val gateway = CompletedChallengesGatewayImplementation(
                codewarsAPI = api,
                dispatcher = dispatcher
            )

            val actualResult = gateway.getCompletedChallenges("name", 1)

            assert(actualResult is Result.Error)
        }
}