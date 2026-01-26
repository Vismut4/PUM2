package com.example.lista5.di

import com.example.lista5.data.CurrencyRepository
import com.example.lista5.data.CurrencyRepositoryImpl
import com.example.lista5.data.FrankfurterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        api: FrankfurterApi
    ): CurrencyRepository =
        CurrencyRepositoryImpl(api)
}
