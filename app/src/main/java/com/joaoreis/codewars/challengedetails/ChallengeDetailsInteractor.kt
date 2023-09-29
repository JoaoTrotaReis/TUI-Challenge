package com.joaoreis.codewars.challengedetails

import com.joaoreis.codewars.State
import kotlinx.coroutines.flow.Flow

interface ChallengeDetailsInteractor {
    val state: Flow<State<ChallengeDetails>>

    suspend fun getChallengeDetails(challengeId: String)
}