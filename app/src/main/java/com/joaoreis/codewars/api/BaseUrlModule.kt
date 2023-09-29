package com.joaoreis.codewars.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {
    @Provides
    fun provideBaseUrl(): String = "https://www.codewars.com/api/v1/"
}