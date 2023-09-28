package com.joaoreis.codewars.completedchallenges

import com.joaoreis.codewars.Result

interface CompletedChallengesGateway {
    suspend fun getCompletedChallenges(userName: String, page: Int): Result<CompletedChallenges>
}