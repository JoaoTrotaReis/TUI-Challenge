package com.joaoreis.codewars.completedchallenges.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesInteractor
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CompletedChallengesViewModel @Inject constructor(
    private val completedChallengesInteractor: CompletedChallengesInteractor
) : ViewModel() {

    private val _viewState = MutableStateFlow<CompletedChallengesViewState>(
        CompletedChallengesViewState.Loading
    )
    val viewState: StateFlow<CompletedChallengesViewState> = _viewState

    init {
        viewModelScope.launch {
            completedChallengesInteractor.state.collect {
                when (it) {
                    is CompletedChallengesState.FirstPageLoadError -> _viewState.emit(CompletedChallengesViewState.Error)
                    is CompletedChallengesState.Loaded -> {
                        _viewState.emit(CompletedChallengesViewState.ChallengesLoaded(
                            it.completedChallenges.challenges.map { challenge ->
                                CompletedChallengeUIModel(
                                    id = challenge.id,
                                    name = challenge.name,
                                    completedAt = formatDate(challenge.completedAt),
                                    languages = challenge.languages
                                )
                            }
                        ))
                    }
                    is CompletedChallengesState.Loading -> _viewState.emit(CompletedChallengesViewState.Loading)
                    else -> {}
                }
            }
        }

        loadCompletedChallenges()
    }

    fun loadCompletedChallenges() {
        viewModelScope.launch {
            completedChallengesInteractor.getCompletedChallenges()
        }
    }

    private fun formatDate(instant: Instant): String {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
            instant.toLocalDateTime(
                TimeZone.UTC
            ).toJavaLocalDateTime()
        )
    }
}