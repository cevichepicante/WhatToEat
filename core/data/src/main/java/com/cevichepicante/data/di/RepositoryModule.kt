package com.cevichepicante.data.di

import com.cevichepicante.data.repository.FoodOrderRepository
import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.data.repository.impl.FoodOrderRepositoryImpl
import com.cevichepicante.data.repository.impl.FoodSourceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsFoodSourceRepository(
        repoImpl: FoodSourceRepositoryImpl
    ): FoodSourceRepository

    @Binds
    abstract fun bindsFoodOrderRepository(
        repoImpl: FoodOrderRepositoryImpl
    ): FoodOrderRepository
}