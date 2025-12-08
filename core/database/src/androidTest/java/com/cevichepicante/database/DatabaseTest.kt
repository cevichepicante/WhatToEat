package com.cevichepicante.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.cevichepicante.database.dao.FoodDao
import com.cevichepicante.database.dao.OrderHistoryDao
import org.junit.After
import org.junit.Before

abstract class DatabaseTest {

    private lateinit var db: WteDatabase
    protected lateinit var foodDao: FoodDao
    protected lateinit var orderHistoryDao: OrderHistoryDao

    @Before
    fun setUp() {
        db = run {
            val context = ApplicationProvider.getApplicationContext<Context>()
            Room.inMemoryDatabaseBuilder(
                context,
                WteDatabase::class.java
            ).build()
        }
        foodDao = db.foodDao()
        orderHistoryDao = db.orderHistoryDao()
    }

    @After
    fun teardown() = db.close()
}