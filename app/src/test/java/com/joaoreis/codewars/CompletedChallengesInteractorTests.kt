package com.joaoreis.codewars

import app.cash.turbine.test
import com.joaoreis.codewars.completedchallenges.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

import org.junit.Test

class CompletedChallengesInteractorTests {
    @Test
    fun `Given there are completed challenges for a user When completed challenges are requested Then emit a loading state followed by a loaded state containing the completed challenges`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val now = Clock.System.now()

        val completedChallenges = CompletedChallenges(
            currentPage = 1,
            challenges = listOf(CompletedChallenge(
                id = "id",
                name = "challenge",
                languages = listOf("java", "c", "kotlin"),
                completedAt = now
            ))
        )
        val expectedResult = State.Loaded(completedChallenges)


        val challengesGateway = mockk<CompletedChallengesGateway>().also {
            coEvery { it.getCompletedChallenges("username", 1) } returns Result.Success(completedChallenges)
        }

        val userGateway = mockk<UserGateway>().also {
            every { it.getCurrentUsername() } returns "username"
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = challengesGateway,
            userGateway = userGateway,
            dispatcher = dispatcher
        )

        interactor.state.test {
            awaitItem()
            interactor.getCompletedChallenges()
            assertTrue(awaitItem() is State.Loading)
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `Given there is an error fetching completed challenges for a user When completed challenges are requested Then emit a loading state followed by an error state `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val challengesGateway = mockk<CompletedChallengesGateway>().also {
            coEvery { it.getCompletedChallenges("username", 1) } returns Result.Error()
        }

        val userGateway = mockk<UserGateway>().also {
            every { it.getCurrentUsername() } returns "username"
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = challengesGateway,
            userGateway = userGateway,
            dispatcher = dispatcher
        )

        interactor.state.test {
            awaitItem()
            interactor.getCompletedChallenges()
            assertTrue(awaitItem() is State.Loading)
            assertTrue(awaitItem() is State.Error)
        }
    }
}