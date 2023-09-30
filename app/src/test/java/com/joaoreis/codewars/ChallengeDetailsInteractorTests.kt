package com.joaoreis.codewars

import app.cash.turbine.test
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetails
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsGateway
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsInteractorImplementation
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ChallengeDetailsInteractorTests {

    @Test
    fun `Given the details for a challenge are available When the details are requested Then emit a loading state followed by a loaded state with the challenge details`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val challengeDetails = ChallengeDetails(
            name = "name",
            description = "description",
            tags = listOf("tag"),
            languages = listOf("kotlin")
        )
        val expectedResult = ChallengeDetailsState.Loaded(challengeDetails)
        val gateway = mockk<ChallengeDetailsGateway>().also {
            coEvery { it.getChallengeDetails(any()) } returns Result.Success(challengeDetails)
        }


        val interactor = ChallengeDetailsInteractorImplementation(
            challengeDetailsGateway = gateway,
            dispatcher = dispatcher
        )

        interactor.state.test {
            awaitItem()
            interactor.getChallengeDetails("id")
            assertTrue(awaitItem() is ChallengeDetailsState.Loading)
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `Given there is an error loading the challenge details When the details are requested Then emit a loading state followed by an error state`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val gateway = mockk<ChallengeDetailsGateway>().also {
            coEvery { it.getChallengeDetails(any()) } returns Result.Error()
        }

        val interactor = ChallengeDetailsInteractorImplementation(
            challengeDetailsGateway = gateway,
            dispatcher = dispatcher
        )

        interactor.state.test {
            awaitItem()
            interactor.getChallengeDetails("id")
            assertTrue(awaitItem() is ChallengeDetailsState.Loading)
            assertTrue(awaitItem() is ChallengeDetailsState.Error)
        }
    }
}