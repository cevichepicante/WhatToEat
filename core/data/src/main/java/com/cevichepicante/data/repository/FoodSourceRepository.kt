package com.cevichepicante.data.repository

import android.content.Context
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodRecipe
import com.cevichepicante.model.FoodSource

interface FoodSourceRepository {

    suspend fun fetchFoodSourceList(context: Context): List<FoodSource>

    suspend fun fetchFoodList(context: Context): List<Food>

    suspend fun fetchRecipe(
        context: Context,
        foodId: String
    ): FoodRecipe?

    fun getMaterialList(materialString: String): List<Pair<String, List<String>>>
}