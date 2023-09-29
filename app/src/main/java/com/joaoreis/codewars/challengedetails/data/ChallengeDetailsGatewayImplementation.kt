package com.joaoreis.codewars.challengedetails.data

import com.joaoreis.codewars.Result
import com.joaoreis.codewars.api.CodewarsAPI
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetails
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsGateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ChallengeDetailsGatewayImplementation(
    private val codewarsAPI: CodewarsAPI,
    private val dispatcher: CoroutineDispatcher
) : ChallengeDetailsGateway {
    override suspend fun getChallengeDetails(challengeId: String): Result<ChallengeDetails> =
        withContext(dispatcher) {
            try {
                Result.Success(codewarsAPI.getChallengeDetails(challengeId).toDomainModel())
            } catch (e: Exception) {
                Result.Error()
            }
        }

    private fun ChallengeDetailsDTO.toDomainModel(): ChallengeDetails {
        return ChallengeDetails(
            this.name,
            this.description,
            this.tags,
            this.languages
        )
    }
}
