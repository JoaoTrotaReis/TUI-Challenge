package com.joaoreis.codewars.challengedetails.data

import com.joaoreis.codewars.IoDispatcher
import com.joaoreis.codewars.api.CodewarsAPI
import com.joaoreis.codewars.challengedetails.domain.ChallengeDetailsGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ChallengeDetailsDataModule {
    @Provides
    fun provideChallengeDetailsGateway(
        codewarsAPI: CodewarsAPI,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): ChallengeDetailsGateway {
        return HttpChallengeDetailsGateway(
            codewarsAPI = codewarsAPI,
            dispatcher = dispatcher
        )
    }
}