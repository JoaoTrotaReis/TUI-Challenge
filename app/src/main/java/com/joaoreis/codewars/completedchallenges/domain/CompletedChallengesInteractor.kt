package com.joaoreis.codewars.completedchallenges.domain

import com.joaoreis.codewars.State
import kotlinx.coroutines.flow.StateFlow

interface CompletedChallengesInteractor {
    val state: StateFlow<State<CompletedChallenges>>

    suspend fun getCompletedChallenges()
}