package com.joaoreis.codewars.challengedetails.domain

sealed class ChallengeDetailsState {
    object Idle : ChallengeDetailsState()
    data class Loaded(val challengeDetails : ChallengeDetails) : ChallengeDetailsState()
    object Error : ChallengeDetailsState()
    object Loading : ChallengeDetailsState()
}
