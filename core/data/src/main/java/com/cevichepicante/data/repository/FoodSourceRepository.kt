package com.cevichepicante.data.repository

import com.cevichepicante.model.Food

interface FoodSourceRepository {

    abstract suspend fun fetchFoodList(): List<Food>
}