package com.cevichepicante.data.repository

import android.content.Context
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodRecipe
import com.cevichepicante.model.FoodSource

interface FoodSourceRepository {

    suspend fun insertFoodSourceList()

    suspend fun fetchFoodSourceList(): List<FoodSource>

    suspend fun fetchFoodList(): List<Food>

    suspend fun fetchFoodDetail(foodId: String): FoodSource?

    suspend fun fetchFoodRecipe(foodId: String): FoodRecipe?

    fun getMaterialList(materialString: String): List<Pair<String, List<String>>>
}