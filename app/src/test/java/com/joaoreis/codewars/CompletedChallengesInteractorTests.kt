package com.joaoreis.codewars

import app.cash.turbine.test
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsState
import com.joaoreis.codewars.completedchallenges.domain.*
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
            totalPages = 1,
            challenges = listOf(CompletedChallenge(
                id = "id",
                name = "challenge",
                languages = listOf("java", "c", "kotlin"),
                completedAt = now
            ))
        )
        val expectedResult = CompletedChallengesState.Loaded(completedChallenges)


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
            assertTrue(awaitItem() is CompletedChallengesState.Loading)
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
            assertTrue(awaitItem() is CompletedChallengesState.Loading)
            assertTrue(awaitItem() is CompletedChallengesState.FirstPageLoadError)
        }
    }

    @Test
    fun `Given there are loaded challenges And next page is available When next page is requested Then emit a loading state followed by a loaded state with both pages of challenges`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val now = Clock.System.now()
        val initialState = CompletedChallengesState.Loaded(
            CompletedChallenges(
                currentPage = 1,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val nextPage = CompletedChallenges(
            currentPage = 2,
            totalPages = 2,
            challenges = listOf(
                CompletedChallenge(
                    id = "id2",
                    name = "name",
                    completedAt = now,
                    languages = listOf()
                )
            )
        )

        val expectedResult = CompletedChallengesState.Loaded(
            CompletedChallenges(
                currentPage = 2,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    ),
                    CompletedChallenge(
                        id = "id2",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val challengesGateway = mockk<CompletedChallengesGateway>().also {
            coEvery { it.getCompletedChallenges("username", 2) } returns Result.Success(nextPage)
        }

        val userGateway = mockk<UserGateway>().also {
            every { it.getCurrentUsername() } returns "username"
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = challengesGateway,
            userGateway = userGateway,
            dispatcher = dispatcher,
            initialState = initialState
        )

        interactor.state.test {
            awaitItem()
            interactor.getMoreCompletedChallenges()
            assertTrue(awaitItem() is CompletedChallengesState.Loading)
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `Given next page failed to load And next page is now available When next page is requested Then emit a loading state followed by a loaded state with both pages of challenges`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val now = Clock.System.now()
        val initialState = CompletedChallengesState.LoadMoreError(
            CompletedChallenges(
                currentPage = 1,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val nextPage = CompletedChallenges(
            currentPage = 2,
            totalPages = 2,
            challenges = listOf(
                CompletedChallenge(
                    id = "id2",
                    name = "name",
                    completedAt = now,
                    languages = listOf()
                )
            )
        )

        val expectedResult = CompletedChallengesState.Loaded(
            CompletedChallenges(
                currentPage = 2,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    ),
                    CompletedChallenge(
                        id = "id2",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val challengesGateway = mockk<CompletedChallengesGateway>().also {
            coEvery { it.getCompletedChallenges("username", 2) } returns Result.Success(nextPage)
        }

        val userGateway = mockk<UserGateway>().also {
            every { it.getCurrentUsername() } returns "username"
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = challengesGateway,
            userGateway = userGateway,
            dispatcher = dispatcher,
            initialState = initialState
        )

        interactor.state.test {
            awaitItem()
            interactor.getMoreCompletedChallenges()
            assertTrue(awaitItem() is CompletedChallengesState.Loading)
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `Given there are loaded challenges And next page is available When next page is requested And fails to load Then emit a loading state followed by a load more error state`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val now = Clock.System.now()
        val initialState = CompletedChallengesState.Loaded(
            CompletedChallenges(
                currentPage = 1,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val expectedResult = CompletedChallengesState.LoadMoreError(
            CompletedChallenges(
                currentPage = 1,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val challengesGateway = mockk<CompletedChallengesGateway>().also {
            coEvery { it.getCompletedChallenges("username", 2) } returns Result.Error()
        }

        val userGateway = mockk<UserGateway>().also {
            every { it.getCurrentUsername() } returns "username"
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = challengesGateway,
            userGateway = userGateway,
            dispatcher = dispatcher,
            initialState = initialState
        )

        interactor.state.test {
            awaitItem()
            interactor.getMoreCompletedChallenges()
            assertTrue(awaitItem() is CompletedChallengesState.Loading)
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `Given next page failed to load When next page is requested And it fails to load Then emit a loading state followed by an load more error state`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val now = Clock.System.now()
        val initialState = CompletedChallengesState.LoadMoreError(
            CompletedChallenges(
                currentPage = 1,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val expectedResult = CompletedChallengesState.LoadMoreError(
            CompletedChallenges(
                currentPage = 1,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val challengesGateway = mockk<CompletedChallengesGateway>().also {
            coEvery { it.getCompletedChallenges("username", 2) } returns Result.Error()
        }

        val userGateway = mockk<UserGateway>().also {
            every { it.getCurrentUsername() } returns "username"
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = challengesGateway,
            userGateway = userGateway,
            dispatcher = dispatcher,
            initialState = initialState
        )

        interactor.state.test {
            awaitItem()
            interactor.getMoreCompletedChallenges()
            assertTrue(awaitItem() is CompletedChallengesState.Loading)
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `Given there are no more pages to load When next page is requested The emit nothing`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val now = Clock.System.now()
        val initialState = CompletedChallengesState.Loaded(
            CompletedChallenges(
                currentPage = 2,
                totalPages = 2,
                challenges = listOf(
                    CompletedChallenge(
                        id = "id",
                        name = "name",
                        completedAt = now,
                        languages = listOf()
                    )
                )
            )
        )

        val challengesGateway = mockk<CompletedChallengesGateway>(relaxed = true)

        val userGateway = mockk<UserGateway>().also {
            every { it.getCurrentUsername() } returns "username"
        }

        val interactor = CompletedChallengesInteractorImplementation(
            completedChallengesGateway = challengesGateway,
            userGateway = userGateway,
            dispatcher = dispatcher,
            initialState = initialState
        )

        interactor.state.test {
            awaitItem()
            interactor.getMoreCompletedChallenges()
            expectNoEvents()
        }
    }
}