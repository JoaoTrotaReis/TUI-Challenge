package com.joaoreis.codewars.completedchallenges.data

import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesGateway
import kotlinx.datetime.Instant

@kotlinx.serialization.Serializable
data class CompletedChallengesListDTO(
    val totalPages: Int,
    val totalItems: Int,
    val data: List<CompletedChallengeDTO>
)

@kotlinx.serialization.Serializable
data class CompletedChallengeDTO(
    val id: String,
    val name: String? = null,
    val completedAt: Instant,
    val completedLanguages: List<String>
)