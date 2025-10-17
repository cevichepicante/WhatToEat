package com.cevichepicante.data.repository.impl

import com.cevichepicante.data.repository.FoodSourceRepository
import com.cevichepicante.model.Food
import javax.inject.Inject

class FoodSourceRepositoryImpl @Inject constructor(
    // TODO api service injected
): FoodSourceRepository {

    override suspend fun fetchFoodList(): List<Food> {
        TODO("Not yet implemented")
    }
}