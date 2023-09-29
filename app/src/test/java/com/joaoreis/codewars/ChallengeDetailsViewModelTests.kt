package com.joaoreis.codewars

import app.cash.turbine.test
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetails
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsInteractor
import com.joaoreis.codewars.challengedetails.presentation.ChallengeDetailsUIModel
import com.joaoreis.codewars.challengedetails.presentation.ChallengeDetailsViewModel
import com.joaoreis.codewars.challengedetails.presentation.ChallengeDetailsViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ChallengeDetailsViewModelTests {

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
    fun `When challenge details are loading Then emit a loading view state`() = runTest {
        val interactor = mockk<ChallengeDetailsInteractor>().also {
            coEvery { it.state } returns MutableStateFlow(State.Loading())
        }

        val viewModel = ChallengeDetailsViewModel(challengeDetailsInteractor = interactor)

        viewModel.viewState.test {
            assertTrue(awaitItem() is ChallengeDetailsViewState.Loading)
        }
    }

    @Test
    fun `When challenge details are loaded Then emit a loaded view state with the challenge details`() = runTest {
        val expectedResult = ChallengeDetailsViewState.ChallengeDetailsLoaded(
            ChallengeDetailsUIModel(
                name = "name",
                description = "description",
                tags = listOf("tag"),
                languages = listOf("kotlin")
            )
        )

        val interactor = mockk<ChallengeDetailsInteractor>().also {
            coEvery { it.state } returns MutableStateFlow(State.Loaded(
                ChallengeDetails(
                    name = "name",
                    description = "description",
                    tags = listOf("tag"),
                    languages = listOf("kotlin")
                )
            ))
        }

        val viewModel = ChallengeDetailsViewModel(challengeDetailsInteractor = interactor)

        viewModel.viewState.test {
            awaitItem()
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `When there is an error loading challenge details Then emit an error view state`() = runTest {
        val interactor = mockk<ChallengeDetailsInteractor>().also {
            coEvery { it.state } returns MutableStateFlow(State.Error())
        }

        val viewModel = ChallengeDetailsViewModel(challengeDetailsInteractor = interactor)

        viewModel.viewState.test {
            awaitItem()
            assertTrue(awaitItem() is ChallengeDetailsViewState.Error)
        }
    }
}