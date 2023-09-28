package com.joaoreis.codewars.completedchallenges

import com.joaoreis.codewars.Result
import com.joaoreis.codewars.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class CompletedChallengesInteractorImplementation(
    private val completedChallengesGateway: CompletedChallengesGateway,
    private val dispatcher: CoroutineDispatcher
): CompletedChallengesInteractor {
    private val _state = MutableStateFlow<State<CompletedChallenges>>(State.Idle())
    override val state: StateFlow<State<CompletedChallenges>> = _state

    override suspend fun getCompletedChallenges(userName: String) {
        withContext(dispatcher) {
            _state.emit(State.Loading())
            when(val result = completedChallengesGateway.getCompletedChallenges(userName)) {
                is Result.Error -> _state.emit(State.Error())
                is Result.Success -> _state.emit(State.Loaded(result.data))
            }
        }
    }
}