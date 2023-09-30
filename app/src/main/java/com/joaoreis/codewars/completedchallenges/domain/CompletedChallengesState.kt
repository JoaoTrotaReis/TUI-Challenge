package com.joaoreis.codewars.completedchallenges.domain

sealed class CompletedChallengesState {
    object Idle: CompletedChallengesState()
    object Loading : CompletedChallengesState()
    data class LoadingMore(val completedChallenges : CompletedChallenges): CompletedChallengesState()
    object FirstPageLoadError : CompletedChallengesState()
    data class Loaded(val completedChallenges : CompletedChallenges) : CompletedChallengesState()
    data class LoadMoreError(val completedChallenges: CompletedChallenges): CompletedChallengesState()
}
