package com.joaoreis.codewars.challengedetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoreis.codewars.State
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ChallengeDetailsViewModel(
    private val challengeDetailsInteractor: ChallengeDetailsInteractor
): ViewModel() {

    private val _viewState = MutableStateFlow<ChallengeDetailsViewState>(ChallengeDetailsViewState.Loading)
    val viewState: StateFlow<ChallengeDetailsViewState> = _viewState

    init {
        viewModelScope.launch {
            challengeDetailsInteractor.state.collect {
                when(it) {
                    is State.Error -> _viewState.emit(ChallengeDetailsViewState.Error)
                    is State.Idle -> {}
                    is State.Loaded -> {
                        _viewState.emit(
                            ChallengeDetailsViewState.ChallengeDetailsLoaded(
                                ChallengeDetailsUIModel(
                                    name = it.data.name,
                                    description = it.data.description,
                                    tags = it.data.tags,
                                    languages = it.data.languages
                                )
                            )
                        )
                    }
                    is State.Loading -> _viewState.emit(ChallengeDetailsViewState.Loading)
                }
            }
        }
    }

    fun loadChallengeDetails(challengeId: String) {
        viewModelScope.launch {
            challengeDetailsInteractor.getChallengeDetails(challengeId)
        }
    }
}