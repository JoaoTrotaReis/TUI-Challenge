package com.joaoreis.codewars.completedchallenges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoreis.codewars.State
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesInteractor
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
): ViewModel() {

    private val _viewState = MutableStateFlow<CompletedChallengesViewState>(CompletedChallengesViewState.Loading)
    val viewState: StateFlow<CompletedChallengesViewState> = _viewState

    init {
       viewModelScope.launch {
           completedChallengesInteractor.state.collect {
               when(it) {
                   is State.Error -> _viewState.emit(CompletedChallengesViewState.Error)
                   is State.Idle -> {}
                   is State.Loaded -> {
                       _viewState.emit(CompletedChallengesViewState.ChallengesLoaded(
                           it.data.challenges.map { challenge ->
                               CompletedChallengeUIModel(
                                   name = challenge.name,
                                   completedAt = formatDate(challenge.completedAt),
                                   languages = challenge.languages
                               )
                           }
                       ))
                   }
                   is State.Loading -> _viewState.emit(CompletedChallengesViewState.Loading)
               }
           }
       }
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