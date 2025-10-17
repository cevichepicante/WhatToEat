package com.cevichepicante.data.repository

import com.cevichepicante.model.FoodRecipe

interface RecipeRepository {

    abstract suspend fun fetchRecipe(foodId: String): FoodRecipe
}