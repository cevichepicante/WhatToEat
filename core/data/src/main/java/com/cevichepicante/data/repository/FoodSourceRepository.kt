package com.cevichepicante.data.repository

import android.content.Context
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodRecipe
import com.cevichepicante.model.FoodSource
import com.cevichepicante.model.FoodType

interface FoodSourceRepository {

    suspend fun insertFoodSourceList(): Boolean

    suspend fun fetchFoodSourceList(): List<FoodSource>

    suspend fun fetchFoodList(): List<Food>

    suspend fun fetchFoodListFiltered(type: FoodType): List<Food>

    suspend fun fetchCookingKindList(): List<String>

    suspend fun fetchCookingMaterialList(): List<String>

    suspend fun fetchCookingOccasionList(): List<String>

    suspend fun fetchFoodDetail(foodId: String): FoodSource?

    suspend fun fetchFoodRecipe(foodId: String): FoodRecipe?

    fun getMaterialList(materialString: String): List<Pair<String, List<String>>>
}