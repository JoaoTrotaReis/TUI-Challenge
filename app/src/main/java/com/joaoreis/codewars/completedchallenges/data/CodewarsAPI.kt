package com.joaoreis.codewars.completedchallenges.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodewarsAPI {
    @GET("users/{user}/code-challenges/completed")
    suspend fun getCompletedChallenges(@Path(value="user") userName: String, @Query("page") page: Int): List<CompletedChallengeDTO>
}