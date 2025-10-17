package com.cevichepicante.data.repository.impl

import com.cevichepicante.data.repository.RecipeRepository
import com.cevichepicante.model.FoodRecipe
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    //  TODO api service injected
): RecipeRepository {

    override suspend fun fetchRecipe(foodId: String): FoodRecipe {
        TODO("Not yet implemented")
    }
}