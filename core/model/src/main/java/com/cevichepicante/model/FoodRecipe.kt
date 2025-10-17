package com.cevichepicante.model

data class FoodRecipe(
    val id: String,
    val name: String,
    val time: String,
    val foodType: String,
    val level: String,
    val ingredients: List<String>,
    val seasonings: List<String>,
) {
}