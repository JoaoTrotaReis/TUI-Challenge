package com.joaoreis.codewars.challengedetails

import com.joaoreis.codewars.Result
import com.joaoreis.codewars.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class ChallengeDetailsInteractorImplementation(
    private val challengeDetailsGateway: ChallengeDetailsGateway,
    private val dispatcher: CoroutineDispatcher
): ChallengeDetailsInteractor {
    private val _state = MutableStateFlow<State<ChallengeDetails>>(State.Idle())
    override val state: Flow<State<ChallengeDetails>> = _state

    override suspend fun getChallengeDetails(challengeId: String) {
        withContext(dispatcher) {
            _state.emit(State.Loading())
            when(val result = challengeDetailsGateway.getChallengeDetails(challengeId)) {
                is Result.Error -> _state.emit(State.Error())
                is Result.Success -> _state.emit(State.Loaded(result.data))
            }
        }
    }
}