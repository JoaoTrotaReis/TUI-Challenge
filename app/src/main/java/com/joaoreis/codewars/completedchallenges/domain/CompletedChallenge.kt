package com.joaoreis.codewars.completedchallenges.domain

import kotlinx.datetime.Instant

data class CompletedChallenge(
    val id: String,
    val name: String,
    val completedAt: Instant,
    val languages: List<String>
)