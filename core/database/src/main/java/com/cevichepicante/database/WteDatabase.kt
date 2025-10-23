package com.cevichepicante.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cevichepicante.database.dao.FoodDao
import com.cevichepicante.database.dao.OrderHistoryDao
import com.cevichepicante.database.model.FoodEntity
import com.cevichepicante.database.model.OrderHistoryEntity

@Database(
    entities = [FoodEntity::class, OrderHistoryEntity::class],
    version = 1
)
abstract class WteDatabase: RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun orderHistoryDao(): OrderHistoryDao
}