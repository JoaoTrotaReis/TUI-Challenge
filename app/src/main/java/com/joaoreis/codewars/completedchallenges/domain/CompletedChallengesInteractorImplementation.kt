package com.joaoreis.codewars.completedchallenges.domain

import androidx.compose.runtime.currentRecomposeScope
import com.joaoreis.codewars.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class CompletedChallengesInteractorImplementation(
    private val completedChallengesGateway: CompletedChallengesGateway,
    private val userGateway: UserGateway,
    private val dispatcher: CoroutineDispatcher,
    initialState: CompletedChallengesState = CompletedChallengesState.Idle
): CompletedChallengesInteractor {
    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<CompletedChallengesState> = _state

    override suspend fun getCompletedChallenges() {
        withContext(dispatcher) {
            _state.emit(CompletedChallengesState.Loading)
            val currentUsername = userGateway.getCurrentUsername()
            when(val result = completedChallengesGateway.getCompletedChallenges(currentUsername, 1)) {
                is Result.Error -> _state.emit(CompletedChallengesState.FirstPageLoadError)
                is Result.Success -> _state.emit(CompletedChallengesState.Loaded(result.data))
            }
        }
    }

    override suspend fun getMoreCompletedChallenges() {
        withContext(dispatcher) {
            val currentUsername = userGateway.getCurrentUsername()
            when(val currentState = state.value) {
                is CompletedChallengesState.LoadMoreError -> {
                    loadNextPage(currentState.completedChallenges, currentState.completedChallenges.currentPage, currentUsername)
                }
                is CompletedChallengesState.Loaded -> {
                    loadNextPage(currentState.completedChallenges, currentState.completedChallenges.currentPage, currentUsername)
                }
                else -> {
                    return@withContext
                }
            }
        }
    }

    private suspend fun loadNextPage(currentChallenges: CompletedChallenges, currentPage: Int, currentUsername: String) {
        if (currentPage == currentChallenges.totalPages) return

        _state.emit(CompletedChallengesState.Loading)
        when(val result = completedChallengesGateway.getCompletedChallenges(currentUsername, currentPage + 1)) {
            is Result.Error -> _state.emit(CompletedChallengesState.LoadMoreError(currentChallenges))
            is Result.Success -> {
                val updatedChallenges = currentChallenges.copy(
                    currentPage = result.data.currentPage,
                    challenges = currentChallenges.challenges + result.data.challenges
                )
                _state.emit(CompletedChallengesState.Loaded(updatedChallenges))
            }
        }
    }
}