package com.cevichepicante.database.di

import com.cevichepicante.database.WteDatabase
import com.cevichepicante.database.dao.FoodDao
import com.cevichepicante.database.dao.OrderHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesFoodDao(
        database: WteDatabase
    ): FoodDao {
        return database.foodDao()
    }

    @Provides
    fun providesOrderHistory(
        database: WteDatabase
    ): OrderHistoryDao {
        return database.orderHistoryDao()
    }
}