package com.cevichepicante.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FoodData")
data class FoodEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "recipeTitle") val recipeTitle: String?,
    @ColumnInfo(name = "cookingName") val cookingName: String?,
    @ColumnInfo(name = "registererId") val registererId: String?,
    @ColumnInfo(name = "registererName") val registererName: String?,
    @ColumnInfo(name = "viewCount") val viewCount: String?,
    @ColumnInfo(name = "recommendedCount") val recommendedCount: String?,
    @ColumnInfo(name = "scrappedCount") val scrappedCount: String?,
    @ColumnInfo(name = "cookingMethodCategory") val cookingMethodCategory: String?,
    @ColumnInfo(name = "cookingOccasionCategory") val cookingOccasionCategory: String?,
    @ColumnInfo(name = "cookingMaterialCategory") val cookingMaterialCategory: String?,
    @ColumnInfo(name = "cookingKindCategory") val cookingKindCategory: String?,
    @ColumnInfo(name = "cookingIntro") val cookingIntro: String?,
    @ColumnInfo(name = "cookingMaterialContent") val cookingMaterialContent: String?,
    @ColumnInfo(name = "cookingAmount") val cookingAmount: String?,
    @ColumnInfo(name = "cookingLevel") val cookingLevel: String?,
    @ColumnInfo(name = "cookingTime") val cookingTime: String?,
    @ColumnInfo(name = "registeredTime") val registeredTime: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?
) {
}