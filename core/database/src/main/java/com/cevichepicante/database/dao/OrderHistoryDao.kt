package com.cevichepicante.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cevichepicante.database.model.OrderHistoryEntity

@Dao
interface OrderHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrderHistory(history: OrderHistoryEntity)

    @Query("select * from OrderHistoryData where id = :orderId")
    fun getHistoryDetail(orderId: String): OrderHistoryEntity?

    @Query("select * from OrderHistoryData where foodId = :foodId")
    fun getHistoryList(foodId: String): List<OrderHistoryEntity>
}