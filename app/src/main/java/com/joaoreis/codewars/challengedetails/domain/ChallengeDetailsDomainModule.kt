package com.joaoreis.codewars.challengedetails.domain

import com.joaoreis.codewars.DefaultDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object ChallengeDetailsDomainModule {

    @Provides
    fun provideChallengeDetailsInteractor(
        challengeDetailsGateway: ChallengeDetailsGateway,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): ChallengeDetailsInteractor {
        return ChallengeDetailsInteractorImplementation(
            challengeDetailsGateway = challengeDetailsGateway,
            dispatcher = dispatcher
        )
    }
}