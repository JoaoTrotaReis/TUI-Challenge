package com.joaoreis.codewars.api

import com.joaoreis.codewars.challengedetails.data.ChallengeDetailsDTO
import com.joaoreis.codewars.completedchallenges.data.CompletedChallengesListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodewarsAPI {
    @GET("users/{user}/code-challenges/completed")
    suspend fun getCompletedChallenges(@Path(value="user") userName: String, @Query("page") page: Int): CompletedChallengesListDTO

    @GET("code-challenges/{challenge}")
    suspend fun getChallengeDetails(@Path(value="challenge") challengeId: String): ChallengeDetailsDTO
}