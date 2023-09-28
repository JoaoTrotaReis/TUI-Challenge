package com.joaoreis.codewars.completedchallenges

import com.joaoreis.codewars.State
import kotlinx.coroutines.flow.StateFlow

interface CompletedChallengesInteractor {
    val state: StateFlow<State<CompletedChallenges>>

    suspend fun getCompletedChallenges(userName: String)
}