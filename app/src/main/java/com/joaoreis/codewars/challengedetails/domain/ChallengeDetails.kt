package com.joaoreis.codewars.challengedetails.domain

data class ChallengeDetails(
    val name: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>
)