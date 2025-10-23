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

    @Query("""
        select * from FoodData
        where (:material = '' or cookingMaterialCategory = :material)
        and (:kind = '' or cookingKindCategory = :kind)
        and (:occasion = '' or cookingOccasionCategory = :occasion)
    """)
    fun getFoodListFiltered(
        material: String,
        kind: String,
        occasion: String
    ): List<FoodEntity>

    @Query("select * from FoodData where id = :id")
    fun getFoodDetail(id: String): FoodEntity?

    @Query("select distinct cookingMaterialCategory from FoodData ")
    fun getCookingMaterialList(): List<String>

    @Query("select distinct cookingKindCategory from FoodData ")
    fun getCookingKindList(): List<String>

    @Query("select distinct cookingOccasionCategory from FoodData ")
    fun getCookingOccasionList(): List<String>
}