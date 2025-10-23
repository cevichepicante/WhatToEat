package com.cevichepicante.model

data class FoodRecipe(
    val id: String,
    val name: String,
    val time: String,
    val foodType: String,
    val level: String,
    val materialList: List<RecipeMaterialData>
) {
}

data class RecipeMaterialData(
    val category: String,
    val list: List<String>
)