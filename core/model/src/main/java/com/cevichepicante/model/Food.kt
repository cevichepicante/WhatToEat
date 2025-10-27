package com.cevichepicante.model

data class Food(
    val id: String,
    val name: String,
    val type: FoodType,
) {

}

data class FoodType(
    val materialCategory: String,
    val kindCategory: String,
    val occasionCategory: String
) {

}