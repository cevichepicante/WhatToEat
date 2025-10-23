package com.cevichepicante.database.di

import android.content.Context
import androidx.room.Room
import com.cevichepicante.database.WteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesWteDatabase(
        @ApplicationContext context: Context
    ): WteDatabase {
        return Room.databaseBuilder(
            context,
            WteDatabase::class.java,
            "wte-database"
        ).build()
    }
}