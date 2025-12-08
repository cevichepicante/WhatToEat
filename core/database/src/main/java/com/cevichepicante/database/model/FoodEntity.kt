package com.cevichepicante.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodRecipe
import com.cevichepicante.model.FoodSource
import com.cevichepicante.model.FoodType
import com.cevichepicante.model.RecipeMaterialData

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
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "price") val price: Int,
) {
}

fun FoodEntity.asFoodModel(): Food {
    val foodType = FoodType(
        materialCategory = cookingMaterialCategory.orEmpty(),
        kindCategory = cookingKindCategory.orEmpty(),
        occasionCategory = cookingOccasionCategory.orEmpty()
    )
    return Food(
        id = id,
        name = cookingName.orEmpty(),
        price = price,
        type = foodType,
    )
}

fun FoodEntity.asFoodSource(): FoodSource {
    return FoodSource(
        serialNo = id,
        recipeTitle = recipeTitle,
        cookingName = cookingName,
        registererId = registererId,
        registererName = registererName,
        viewCount = viewCount,
        recommendedCount = recommendedCount,
        scrappedCount = scrappedCount,
        cookingMethodCategory = cookingMethodCategory,
        cookingOccasionCategory = cookingOccasionCategory,
        cookingMaterialCategory = cookingMaterialCategory,
        cookingKindCategory = cookingKindCategory,
        cookingIntro = cookingIntro,
        cookingMaterialContent = cookingMaterialContent,
        cookingAmount = cookingAmount,
        cookingLevel = cookingLevel,
        cookingTime = cookingTime,
        registeredTime = registeredTime,
        imageUrl = imageUrl
    )
}