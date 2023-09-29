package com.joaoreis.codewars.challengedetails

import com.joaoreis.codewars.Result

interface ChallengeDetailsGateway {
    suspend fun getChallengeDetails(challengeId: String): Result<ChallengeDetails>
}