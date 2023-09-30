package com.joaoreis.codewars.completedchallenges.domain

data class CompletedChallenges(
    val currentPage: Int,
    val totalPages: Int,
    val challenges: List<CompletedChallenge>
)
