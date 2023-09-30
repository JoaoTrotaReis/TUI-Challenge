package com.joaoreis.codewars.challengedetails.domain

import kotlinx.coroutines.flow.Flow

interface ChallengeDetailsInteractor {
    val state: Flow<ChallengeDetailsState>

    suspend fun getChallengeDetails(challengeId: String)
}