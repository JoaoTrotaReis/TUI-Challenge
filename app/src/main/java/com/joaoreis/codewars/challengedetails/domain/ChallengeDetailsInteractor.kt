package com.joaoreis.codewars.challengedetails.domain

import com.joaoreis.codewars.State
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetails
import kotlinx.coroutines.flow.Flow

interface ChallengeDetailsInteractor {
    val state: Flow<State<ChallengeDetails>>

    suspend fun getChallengeDetails(challengeId: String)
}