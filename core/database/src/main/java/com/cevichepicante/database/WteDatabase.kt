package com.cevichepicante.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cevichepicante.database.dao.FoodDao
import com.cevichepicante.database.dao.OrderHistoryDao
import com.cevichepicante.database.model.FoodEntity
import com.cevichepicante.database.model.OrderHistoryEntity

@Database(
    entities = [FoodEntity::class, OrderHistoryEntity::class],
    version = 1
)
abstract class WteDatabase: RoomDatabase() {

    companion object {
        val MIGRATION_1_TO_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.apply {
                    execSQL("ALTER TABLE FoodData ADD COLUMN price INTEGER")
                    execSQL("ALTER TABLE OrderHistoryData ADD COLUMN amount INTEGER")
                }
            }
        }
    }

    abstract fun foodDao(): FoodDao
    abstract fun orderHistoryDao(): OrderHistoryDao
}