package com.joaoreis.codewars.completedchallenges.domain

import com.joaoreis.codewars.State
import kotlinx.coroutines.flow.Flow

interface CompletedChallengesInteractor {
    val state: Flow<State<CompletedChallenges>>

    suspend fun getCompletedChallenges()
}