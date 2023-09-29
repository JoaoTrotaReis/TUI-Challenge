package com.joaoreis.codewars.completedchallenges.data

import com.joaoreis.codewars.api.CodewarsAPI
import com.joaoreis.codewars.Result
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenge
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallenges
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesGateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CompletedChallengesGatewayImplementation(
    val codewarsAPI: CodewarsAPI,
    val dispatcher: CoroutineDispatcher
) : CompletedChallengesGateway {
    override suspend fun getCompletedChallenges(userName: String, page: Int): Result<CompletedChallenges> =
        withContext(dispatcher) {
            try {
                val response = codewarsAPI.getCompletedChallenges(userName, page)
                Result.Success(CompletedChallenges(
                    currentPage = page,
                    challenges = response.data.map { it.toDomainEntity() }
                ))
            } catch (e: Exception) {
                Result.Error()
            }
        }

    private fun CompletedChallengeDTO.toDomainEntity(): CompletedChallenge = CompletedChallenge(
        id = this.id,
        name = this.name,
        completedAt = this.completedAt,
        languages = this.completedLanguages
    )
}



