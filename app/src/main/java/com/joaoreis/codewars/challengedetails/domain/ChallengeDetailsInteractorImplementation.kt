package com.joaoreis.codewars.challengedetails.domain

import com.joaoreis.codewars.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class ChallengeDetailsInteractorImplementation(
    private val challengeDetailsGateway: ChallengeDetailsGateway,
    private val dispatcher: CoroutineDispatcher
): ChallengeDetailsInteractor {
    private val _state = MutableStateFlow<ChallengeDetailsState>(ChallengeDetailsState.Idle)
    override val state: Flow<ChallengeDetailsState> = _state

    override suspend fun getChallengeDetails(challengeId: String) {
        withContext(dispatcher) {
            _state.emit(ChallengeDetailsState.Loading)
            when(val result = challengeDetailsGateway.getChallengeDetails(challengeId)) {
                is Result.Error -> _state.emit(ChallengeDetailsState.Error)
                is Result.Success -> _state.emit(ChallengeDetailsState.Loaded(result.data))
            }
        }
    }
}