package com.joaoreis.codewars.challengedetails.domain

import com.joaoreis.codewars.Result
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetails

interface ChallengeDetailsGateway {
    suspend fun getChallengeDetails(challengeId: String): Result<ChallengeDetails>
}