package com.joaoreis.codewars

import app.cash.turbine.test
import com.joaoreis.codewars.completedchallenges.CompletedChallenge
import com.joaoreis.codewars.completedchallenges.CompletedChallenges
import com.joaoreis.codewars.completedchallenges.CompletedChallengesGateway
import com.joaoreis.codewars.completedchallenges.CompletedChallengesInteractorImplementation
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

import org.junit.Test

class CompletedChallengesInteractorTests {
    @Test
    fun `Given there are completed challenges for a user When completed challenges are requested Then emit a loading state followed by a loaded state containing the completed challenges`() = runTest {
        val completedChallenges = CompletedChallenges(
            currentPage = 1,
            challenges = listOf(CompletedChallenge(
                id = "id",
                name = "challenge",
                languages = listOf("java", "c", "kotlin"),
                completedAt = "123"
            ))
        )
        val expectedResult = State.Loaded(completedChallenges)
        val dispatcher = StandardTestDispatcher(testScheduler)

        val gateway = mockk<CompletedChallengesGateway>().also {
            coEvery { it.getCompletedChallenges(any()) } returns Result.Success(completedChallenges)
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = gateway,
            dispatcher = dispatcher
        )

        interactor.state.test {
            awaitItem()
            interactor.getCompletedChallenges("user name")
            assertTrue(awaitItem() is State.Loading)
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `Given there is an error fetching completed challenges for a user When completed challenges are requested Then emit a loading state followed by an error state `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val gateway = mockk<CompletedChallengesGateway>().also {
            coEvery { it.getCompletedChallenges(any()) } returns Result.Error()
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = gateway,
            dispatcher = dispatcher
        )

        interactor.state.test {
            awaitItem()
            interactor.getCompletedChallenges("user name")
            assertTrue(awaitItem() is State.Loading)
            assertTrue(awaitItem() is State.Error)
        }
    }
}