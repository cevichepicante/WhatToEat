package com.cevichepicante.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OrderHistoryData")
data class OrderHistoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "foodId") val foodId: String,
    @ColumnInfo(name = "amount") val foodAmount: Int,
    @ColumnInfo(name = "clientName") val clientName: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "orderDate") val orderDate: String,
    @ColumnInfo(name = "delivererNumber") val delivererNumber: String,
) {
}