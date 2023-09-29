package com.joaoreis.codewars.completedchallenges.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.joaoreis.codewars.IoDispatcher
import com.joaoreis.codewars.completedchallenges.domain.CompletedChallengesGateway
import com.joaoreis.codewars.completedchallenges.domain.UserGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val BASE_URL = "https://www.codewars.com/api/v1/"

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideCodewarsApi(retrofit: Retrofit): CodewarsAPI =
        retrofit.create(CodewarsAPI::class.java)

    @Singleton
    @Provides
    fun provideChallengesGateway(
        codewarsAPI: CodewarsAPI,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CompletedChallengesGateway =
        CompletedChallengesGatewayImplementation(codewarsAPI, dispatcher)

    @Singleton
    @Provides
    fun providesUserGateway(): UserGateway = UserGatewayImplementation()
}