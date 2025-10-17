package com.cevichepicante.model

data class FoodSource(
    val serialNo: String,
    val recipeTitle: String?,
    val cookingName: String?,
    val registererId: String?,
    val registererName: String?,
    val viewCount: String?,
    val recommendedCount: String?,
    val scrappedCount: String?,
    val cookingMethodCategory: String?,
    val cookingOccasionCategory: String?,
    val cookingMaterialCategory: String?,
    val cookingKindCategory: String?,
    val cookingIntro: String?,
    val cookingMaterialContent: String?,
    val cookingAmount: String?,
    val cookingLevel: String?,
    val cookingTime: String?,
    val registeredTime: String?,
) {
}