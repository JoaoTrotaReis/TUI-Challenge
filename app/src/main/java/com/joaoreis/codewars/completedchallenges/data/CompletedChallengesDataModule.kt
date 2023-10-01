package com.joaoreis.codewars.completedchallenges.data

import com.joaoreis.codewars.IoDispatcher
import com.joaoreis.codewars.api.CodewarsAPI
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesGateway
import com.joaoreis.codewars.completedchallenges.domain.UserGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompletedChallengesDataModule {
    @Singleton
    @Provides
    fun provideChallengesGateway(
        codewarsAPI: CodewarsAPI,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CompletedChallengesGateway =
        HttpCompletedChallengesGateway(codewarsAPI, dispatcher)

    @Singleton
    @Provides
    fun providesUserGateway(): UserGateway = UserGatewayImplementation()
}