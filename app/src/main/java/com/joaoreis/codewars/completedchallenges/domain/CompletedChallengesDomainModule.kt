package com.joaoreis.codewars.completedchallenges.domain

import com.joaoreis.codewars.DefaultDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CompletedChallengesDomainModule {
    @Provides
    fun provideCompletedChallengesInteractor(
        challengesGateway: CompletedChallengesGateway,
        userGateway: UserGateway,
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): CompletedChallengesInteractor {
        return CompletedChallengesInteractorImplementation(
            challengesGateway,
            userGateway,
            dispatcher
        )
    }
}