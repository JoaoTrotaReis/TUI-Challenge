package com.joaoreis.codewars.challengedetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsState
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailsViewModel @Inject constructor(
    private val challengeDetailsInteractor: ChallengeDetailsInteractor
): ViewModel() {

    private val _viewState = MutableStateFlow<ChallengeDetailsViewState>(ChallengeDetailsViewState.Loading)
    val viewState: StateFlow<ChallengeDetailsViewState> = _viewState

    init {
        viewModelScope.launch {
            challengeDetailsInteractor.state.collect {
                when(it) {
                    is ChallengeDetailsState.Error -> _viewState.emit(ChallengeDetailsViewState.Error)
                    is ChallengeDetailsState.Idle -> {}
                    is ChallengeDetailsState.Loaded -> {
                        _viewState.emit(
                            ChallengeDetailsViewState.ChallengeDetailsLoaded(
                                ChallengeDetailsUIModel(
                                    name = it.challengeDetails.name,
                                    description = it.challengeDetails.description,
                                    tags = it.challengeDetails.tags,
                                    languages = it.challengeDetails.languages
                                )
                            )
                        )
                    }
                    is ChallengeDetailsState.Loading -> _viewState.emit(ChallengeDetailsViewState.Loading)
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