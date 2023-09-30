package com.joaoreis.codewars

import app.cash.turbine.test
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenge
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenges
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesInteractor
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesState
import com.joaoreis.codewars.completedchallenges.presentation.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
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
        val interactor = mockk<CompletedChallengesInteractor>(relaxed = true).also {
            coEvery { it.state } returns MutableStateFlow(CompletedChallengesState.Loading)
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
        val interactor = mockk<CompletedChallengesInteractor>(relaxed = true).also {
            coEvery { it.state } returns MutableStateFlow(CompletedChallengesState.FirstPageLoadError)
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
    fun `When the completed challenges were loaded And there are more pages Then emit a loaded view state with challenges And allow load more`() = runTest {
        val challenges = CompletedChallenges(
            currentPage = 1,
            totalPages = 2,
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
                    id = "id",
                    name = "name",
                    completedAt = "28/09/2023",
                    languages = listOf("kotlin")
                )
            ), canLoadMore = true
        )

        val interactor = mockk<CompletedChallengesInteractor>(relaxed = true).also {
            coEvery { it.state } returns MutableStateFlow(CompletedChallengesState.Loaded(challenges))
        }

        val viewModel = CompletedChallengesViewModel(
            completedChallengesInteractor = interactor
        )

        viewModel.viewState.test {
            awaitItem()
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `When the completed challenges were loaded And there are no more pages Then emit a loaded view state with challenges And do not allow load more`() = runTest {
        val challenges = CompletedChallenges(
            currentPage = 2,
            totalPages = 2,
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
                    id = "id",
                    name = "name",
                    completedAt = "28/09/2023",
                    languages = listOf("kotlin")
                )
            ), canLoadMore = false
        )

        val interactor = mockk<CompletedChallengesInteractor>(relaxed = true).also {
            coEvery { it.state } returns MutableStateFlow(CompletedChallengesState.Loaded(challenges))
        }

        val viewModel = CompletedChallengesViewModel(
            completedChallengesInteractor = interactor
        )

        viewModel.viewState.test {
            awaitItem()
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `When next page is being loaded Then emit a view state with current challenges And a loading more item at the end`() = runTest {
        val challenges = CompletedChallenges(
            currentPage = 1,
            totalPages = 2,
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
                    id = "id",
                    name = "name",
                    completedAt = "28/09/2023",
                    languages = listOf("kotlin")
                ), LoadingMore
            ), canLoadMore = true
        )

        val interactor = mockk<CompletedChallengesInteractor>(relaxed = true).also {
            coEvery { it.state } returns MutableStateFlow(CompletedChallengesState.LoadingMore(challenges))
        }

        val viewModel = CompletedChallengesViewModel(
            completedChallengesInteractor = interactor
        )

        viewModel.viewState.test {
            awaitItem()
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `When next page fails to load Then emit a view state with current challenges And a load more error item at the end`() = runTest {
        val challenges = CompletedChallenges(
            currentPage = 1,
            totalPages = 2,
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
                    id = "id",
                    name = "name",
                    completedAt = "28/09/2023",
                    languages = listOf("kotlin")
                ), LoadMoreError
            ), canLoadMore = true
        )

        val interactor = mockk<CompletedChallengesInteractor>(relaxed = true).also {
            coEvery { it.state } returns MutableStateFlow(CompletedChallengesState.LoadMoreError(challenges))
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