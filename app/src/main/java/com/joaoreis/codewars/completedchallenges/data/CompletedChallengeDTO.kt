package com.joaoreis.codewars.completedchallenges.data

import kotlinx.datetime.Instant

@kotlinx.serialization.Serializable
data class CompletedChallengeDTO(
    val id: String,
    val name: String,
    val slug: String,
    val completedAt: Instant,
    val completedLanguages: List<String>
)