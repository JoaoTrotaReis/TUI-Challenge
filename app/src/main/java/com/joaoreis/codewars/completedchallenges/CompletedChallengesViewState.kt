package com.joaoreis.codewars.completedchallenges

data class CompletedChallengeUIModel(
    val name: String,
    val completedAt: String,
    val languages: List<String>
)

sealed class CompletedChallengesViewState {
    object Loading: CompletedChallengesViewState()
    data class ChallengesLoaded(val challenges: List<CompletedChallengeUIModel>): CompletedChallengesViewState()
    object Error: CompletedChallengesViewState()
}

