package com.joaoreis.codewars.challengedetails.presentation

data class ChallengeDetailsUIModel(
    val name: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>
)

sealed class ChallengeDetailsViewState {
    object Loading: ChallengeDetailsViewState()
    data class ChallengeDetailsLoaded(val challenge: ChallengeDetailsUIModel): ChallengeDetailsViewState()
    object Error: ChallengeDetailsViewState()
}