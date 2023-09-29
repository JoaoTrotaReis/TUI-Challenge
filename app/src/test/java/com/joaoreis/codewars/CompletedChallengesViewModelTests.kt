package com.joaoreis.codewars

import app.cash.turbine.test
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengeUIModel
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengesViewModel
import com.joaoreis.codewars.completedchallenges.presentation.CompletedChallengesViewState
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenge
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenges
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesInteractor
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CompletedChallengesViewModelTests {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When completed challenges are loading Then emit a loading view state`() = runTest {
        val interactor = mockk<CompletedChallengesInteractor>().also {
            coEvery { it.state } returns MutableStateFlow(State.Loading())
        }

        val viewModel = CompletedChallengesViewModel(
            completedChallengesInteractor = interactor
        )

        viewModel.viewState.test {
            assertEquals(CompletedChallengesViewState.Loading, awaitItem())
        }
    }

    @Test
    fun `When there is an error getting completed challenges Then emit an error view state`() = runTest {
        val interactor = mockk<CompletedChallengesInteractor>().also {
            coEvery { it.state } returns MutableStateFlow(State.Error())
        }

        val viewModel = CompletedChallengesViewModel(
            completedChallengesInteractor = interactor
        )

        viewModel.viewState.test {
            awaitItem()
            assertEquals(CompletedChallengesViewState.Error, awaitItem())
        }
    }

    @Test
    fun `When the completed challenges were loaded Then emit a loaded view state`() = runTest {
        val challenges = CompletedChallenges(
            currentPage = 1,
            challenges = listOf(
                CompletedChallenge(
                    id = "id",
                    name = "name",
                    completedAt = Instant.parse("2023-09-28T13:00:00Z"),
                    languages = listOf("kotlin")
                )
            )
        )

        val expectedResult = CompletedChallengesViewState.ChallengesLoaded(
            challenges = listOf(
                CompletedChallengeUIModel(
                    name = "name",
                    completedAt = "28/09/2023",
                    languages = listOf("kotlin")
                )
            )
        )

        val interactor = mockk<CompletedChallengesInteractor>().also {
            coEvery { it.state } returns MutableStateFlow(State.Loaded(challenges))
        }

        val viewModel = CompletedChallengesViewModel(
            completedChallengesInteractor = interactor
        )

        viewModel.viewState.test {
            awaitItem()
            assertEquals(expectedResult, awaitItem())
        }
    }
}