package com.joaoreis.codewars.challengedetails.data

@kotlinx.serialization.Serializable
data class ChallengeDetailsDTO(
    val name: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>
)