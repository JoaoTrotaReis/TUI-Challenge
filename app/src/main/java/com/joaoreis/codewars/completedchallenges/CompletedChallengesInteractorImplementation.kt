package com.joaoreis.codewars.completedchallenges

import com.joaoreis.codewars.Result
import com.joaoreis.codewars.State
import com.joaoreis.codewars.completedchallenges.data.UserGatewayImplementation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class CompletedChallengesInteractorImplementation(
    private val completedChallengesGateway: CompletedChallengesGateway,
    private val userGateway: UserGateway,
    private val dispatcher: CoroutineDispatcher
): CompletedChallengesInteractor {
    private val _state = MutableStateFlow<State<CompletedChallenges>>(State.Idle())
    override val state: StateFlow<State<CompletedChallenges>> = _state

    override suspend fun getCompletedChallenges() {
        withContext(dispatcher) {
            _state.emit(State.Loading())
            val currentUsername = userGateway.getCurrentUsername()
            when(val result = completedChallengesGateway.getCompletedChallenges(currentUsername, 1)) {
                is Result.Error -> _state.emit(State.Error())
                is Result.Success -> _state.emit(State.Loaded(result.data))
            }
        }
    }
}