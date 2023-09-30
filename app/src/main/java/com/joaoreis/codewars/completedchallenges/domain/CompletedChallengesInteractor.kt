package com.joaoreis.codewars.completedchallenges.domain

import kotlinx.coroutines.flow.Flow

interface CompletedChallengesInteractor {
    val state: Flow<CompletedChallengesState>

    suspend fun getCompletedChallenges()
    suspend fun getMoreCompletedChallenges()
}