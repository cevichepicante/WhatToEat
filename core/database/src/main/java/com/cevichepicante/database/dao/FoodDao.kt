package com.cevichepicante.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cevichepicante.database.model.FoodEntity

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFoodList(list: List<FoodEntity>)

    @Query("select * from FoodData")
    fun getFoodList(): List<FoodEntity>

    @Query("select * from FoodData where id = :id")
    fun getFoodDetail(id: String): FoodEntity?
}