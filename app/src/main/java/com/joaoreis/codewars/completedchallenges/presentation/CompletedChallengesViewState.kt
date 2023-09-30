package com.joaoreis.codewars.completedchallenges.presentation

sealed class CompletedChallengeListItem

data class CompletedChallengeUIModel(
    val id: String,
    val name: String,
    val completedAt: String,
    val languages: List<String>
): CompletedChallengeListItem()
object LoadingMore: CompletedChallengeListItem()
object LoadMoreError: CompletedChallengeListItem()


sealed class CompletedChallengesViewState {
    object Loading: CompletedChallengesViewState()
    data class ChallengesLoaded(val challenges: List<CompletedChallengeUIModel>): CompletedChallengesViewState()
    object Error: CompletedChallengesViewState()
}

