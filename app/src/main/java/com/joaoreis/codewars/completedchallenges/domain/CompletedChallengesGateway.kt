package com.joaoreis.codewars.completedchallenges.domain

import com.joaoreis.codewars.Result

interface CompletedChallengesGateway {
    suspend fun getCompletedChallenges(userName: String, page: Int): Result<CompletedChallenges>
}